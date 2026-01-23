const { Kafka, CompressionTypes, CompressionCodecs } = require('kafkajs')
const SnappyCodec = require('kafkajs-snappy')

CompressionCodecs[CompressionTypes.Snappy] = SnappyCodec

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ['localhost:19092']
})

async function main() {
    const consumer = kafka.consumer({ groupId: 'chat-group' })
    await consumer.connect()
    await consumer.subscribe({ topic: 'chat-room', fromBeginning: false })
    
    await consumer.run({
        autoCommitInterval: 5000,
        autoCommitThreshold: 100,
        autoCommit: true,
        eachMessage: async ({ topic, partition, message }) => {
            
            try {
                    console.log({value: message.value.toString()});
            } catch (err) {
                //void
            }
        },
    })
}

main();
