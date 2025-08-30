# ALGORITHM

### Inventors of RSA, Ed25519, and ECDSA

#### RSA (Rivest–Shamir–Adleman)
- **Inventors**: Ron Rivest, Adi Shamir, and Leonard Adleman
- **Year**: Publicly described in 1977
- **Details**: RSA is a public-key cryptosystem based on the difficulty of factoring large prime numbers. It was developed by Ron Rivest, Adi Shamir, and Leonard Adleman at MIT. An equivalent system was secretly developed in 1973 by Clifford Cocks at GCHQ (declassified in 1997).[](https://en.wikipedia.org/wiki/RSA_%28cryptosystem%29)[](https://www.britannica.com/topic/RSA-encryption)
- **Context**: The trio founded RSA Security LLC in 1982, which became a major player in encryption standards and products like SecurID.[](https://en.wikipedia.org/wiki/RSA_Security)

#### Ed25519 (Edwards-curve Digital Signature Algorithm, EdDSA variant)
- **Inventors**: Daniel J. Bernstein, Niels Duif, Tanja Lange, Peter Schwabe, and Bo-Yin Yang
- **Year**: Introduced around 2011 (standardized in RFC 8032, 2017)
- **Details**: Ed25519 is a high-speed, secure digital signature scheme based on the Curve25519 elliptic curve, using a deterministic Schnorr signature variant. It was designed to resist side-channel attacks and avoid random number generator vulnerabilities that affect other schemes like ECDSA. The team, led by Bernstein, released the reference implementation as public-domain software.[](https://en.wikipedia.org/wiki/EdDSA)[](https://ed25519.cr.yp.to/)[](https://cryptobook.nakov.com/digital-signatures/eddsa-and-ed25519)

#### ECDSA (Elliptic Curve Digital Signature Algorithm)
- **Inventors**: Attributed to Scott Vanstone, Don Johnson, and Alfred Menezes (formalized in 2001, though based on earlier elliptic curve work)
- **Year**: Standardized in 2001 by Johnson, Menezes, and Vanstone
- **Details**: ECDSA is a variant of the Digital

System: Signature Algorithm (DSA) that uses elliptic curve cryptography for smaller key sizes and faster performance compared to RSA. It was adopted in standards like ANSI X9.62 and NIST’s FIPS 186-2. Scott Vanstone, a key figure in elliptic curve cryptography, co-founded Certicom, which promoted ECC-based algorithms.[](https://billatnapier.medium.com/a-bluffers-guide-to-eddsa-and-ecdsa-08f578447c57)[](https://gpgfrontend.bktus.com/extra/algorithms-comparison/)
