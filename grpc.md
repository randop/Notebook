# GRPC

Below is a complete guide to developing a simple gRPC server and client application in C++. We'll use the classic "Hello World" example from the gRPC documentation, where the client sends a name to the server, and the server responds with a greeting.

This assumes you have:
- C++11 or later compiler (e.g., g++).
- gRPC installed on your system. You can install it via package managers:
  - On Ubuntu: `sudo apt install libgrpc++-dev protobuf-compiler-grpc`.
  - On macOS: `brew install grpc`.
  - Via vcpkg (cross-platform): `vcpkg install grpc`.
- Protocol Buffers compiler (`protoc`) installed (usually comes with gRPC).

If you encounter build issues, refer to the official gRPC C++ quickstart: https://grpc.io/docs/languages/cpp/quickstart/.

### Step 1: Define the Protocol (`.proto` File)
Create a file named `helloworld.proto` with the following content. This defines the service, RPC method, and message types.

```proto
syntax = "proto3";

option cc_generic_services = true;

package helloworld;

// The greeting service definition.
service Greeter {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}
```

### Step 2: Generate C++ Code from the `.proto` File
Run the Protocol Buffers compiler to generate the C++ headers and sources:

```bash
protoc --grpc_out=. --plugin=protoc-gen-grpc=$(which grpc_cpp_plugin) helloworld.proto
protoc --cpp_out=. helloworld.proto
```

This will produce:
- `helloworld.pb.h` and `helloworld.pb.cc` (Protocol Buffers code).
- `helloworld.grpc.pb.h` and `helloworld.grpc.pb.cc` (gRPC-specific code).

### Step 3: Implement the gRPC Server
Create a file named `greeter_server.cc` with the following content. This implements the `Greeter` service and starts the server listening on port 50051.

```cpp
#include <iostream>
#include <memory>
#include <string>

#include <grpcpp/grpcpp.h>
#include "helloworld.grpc.pb.h"

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;
using helloworld::Greeter;
using helloworld::HelloReply;
using helloworld::HelloRequest;

// Logic and data behind the server's behavior.
class GreeterServiceImpl final : public Greeter::Service {
  Status SayHello(ServerContext* context, const HelloRequest* request,
                  HelloReply* reply) override {
    std::string prefix("Hello ");
    reply->set_message(prefix + request->name());
    return Status::OK;
  }
};

void RunServer() {
  std::string server_address("0.0.0.0:50051");
  GreeterServiceImpl service;

  ServerBuilder builder;
  // Listen on the given address without any authentication mechanism.
  builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
  // Register "service" as the instance through which we'll communicate with
  // clients. In this case it corresponds to an *synchronous* service.
  builder.RegisterService(&service);
  // Finally assemble the server.
  std::unique_ptr<Server> server(builder.BuildAndStart());
  std::cout << "Server listening on " << server_address << std::endl;

  // Wait for the server to shutdown. Note that some other thread must be
  // responsible for shutting down the server for this call to ever return.
  server->Wait();
}

int main(int argc, char** argv) {
  RunServer();
  return 0;
}
```

#### Compile the Server
```bash
g++ -std=c++11 greeter_server.cc helloworld.pb.cc helloworld.grpc.pb.cc -o greeter_server `pkg-config --cflags --libs grpc++ protobuf`
```

#### Run the Server
```bash
./greeter_server
```
It will print: `Server listening on 0.0.0.0:50051` and block until interrupted (e.g., Ctrl+C).

### Step 4: Implement the gRPC Client
Create a file named `greeter_client.cc` with the following content. This creates a client that connects to the server, sends a request, and prints the response.

```cpp
#include <iostream>
#include <memory>
#include <string>

#include <grpcpp/grpcpp.h>
#include "helloworld.grpc.pb.h"

using grpc::Channel;
using grpc::ClientContext;
using grpc::Status;
using helloworld::Greeter;
using helloworld::HelloReply;
using helloworld::HelloRequest;

class GreeterClient {
 public:
  GreeterClient(std::shared_ptr<Channel> channel)
      : stub_(Greeter::NewStub(channel)) {}

  // Assembles the client's payload, sends it and presents the response back
  // from the server.
  std::string SayHello(const std::string& user) {
    // Data we are sending to the server.
    HelloRequest request;
    request.set_name(user);

    // Container for the data we expect from the server.
    HelloReply reply;

    // Context for the client. It could be used to convey extra information to
    // the server and/or tweak certain RPC behaviors.
    ClientContext context;

    // The actual RPC.
    Status status = stub_->SayHello(&context, request, &reply);

    // Act upon its status.
    if (status.ok()) {
      return reply.message();
    } else {
      std::cout << status.error_code() << ": " << status.error_message()
                << std::endl;
      return "RPC failed";
    }
  }

 private:
  std::unique_ptr<Greeter::Stub> stub_;
};

int main(int argc, char** argv) {
  // Instantiate the client. It requires a channel, out of which the actual RPCs
  // are created. This channel models a connection to an endpoint specified by
  // the argument "target_str" which is the only expected argument.
  // We indicate that the channel isn't authenticated (use of
  // InsecureChannelCredentials()).
  std::string target_str;
  std::string user;
  // Use "localhost:50051" if running locally.
  target_str = (argc > 1) ? argv[1] : "localhost:50051";
  user = (argc > 2) ? argv[2] : "world";
  GreeterClient greeter(
      grpc::CreateChannel(target_str, grpc::InsecureChannelCredentials()));
  std::cout << "Greeter received: " << greeter.SayHello(user) << std::endl;

  return 0;
}
```

#### Compile the Client
```bash
g++ -std=c++11 greeter_client.cc helloworld.pb.cc helloworld.grpc.pb.cc -o greeter_client `pkg-config --cflags --libs grpc++ protobuf`
```

#### Run the Client
With the server running in another terminal:
```bash
./greeter_client localhost:50051 "Alice"
```
Expected output: `Greeter received: Hello Alice`

### Additional Notes
- **Security**: This uses insecure credentials (no TLS). For production, use `grpc::SslServerCredentials` or `grpc::SslCredentials` with certificates.
- **Error Handling**: The code includes basic status checks; expand as needed.
- **Extensions**: To add more RPC methods, update the `.proto` file and regenerate code.
- **Testing**: Run the server, then multiple clients to verify.
- **Dependencies**: If `pkg-config` fails, manually link libraries (e.g., `-lgrpc++ -lprotobuf -lpthread`).
- For async or streaming RPCs, refer to gRPC docs: https://grpc.io/docs/languages/cpp/.

This is a minimal, working example. If you need expansions (e.g., unary/streaming calls, authentication), provide more details!
