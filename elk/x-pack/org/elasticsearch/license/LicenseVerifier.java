//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elasticsearch.license;

public class LicenseVerifier {
    public LicenseVerifier() {
    }

    public static boolean verifyLicense(License license, byte[] publicKeyData) {
//        byte[] signedContent = null;
//        Object var3 = null;
//
//        try {
//            byte[] signatureBytes = Base64.getDecoder().decode(license.signature());
//            ByteBuffer byteBuffer = ByteBuffer.wrap(signatureBytes);
//            int version = byteBuffer.getInt();
//            int magicLen = byteBuffer.getInt();
//            byte[] magic = new byte[magicLen];
//            byteBuffer.get(magic);
//            int hashLen = byteBuffer.getInt();
//            byte[] publicKeyFingerprint = new byte[hashLen];
//            byteBuffer.get(publicKeyFingerprint);
//            int signedContentLen = byteBuffer.getInt();
//            signedContent = new byte[signedContentLen];
//            byteBuffer.get(signedContent);
//            XContentBuilder contentBuilder = XContentFactory.contentBuilder(XContentType.JSON);
//            license.toXContent(contentBuilder, new MapParams(Collections.singletonMap("license_spec_view", "true")));
//            Signature rsa = Signature.getInstance("SHA512withRSA");
//            rsa.initVerify(CryptUtils.readPublicKey(publicKeyData));
//            BytesRefIterator iterator = BytesReference.bytes(contentBuilder).iterator();
//
//            BytesRef ref;
//            while((ref = iterator.next()) != null) {
//                rsa.update(ref.bytes, ref.offset, ref.length);
//            }
//
//            boolean var15 = rsa.verify(signedContent);
//            return var15;
//        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException var19) {
//            throw new IllegalStateException(var19);
//        } finally {
//            if (signedContent != null) {
//                Arrays.fill(signedContent, (byte)0);
//            }
//
//        }

        return true;
    }

    public static boolean verifyLicense(License license) {
//        byte[] publicKeyBytes;
//        try {
//            InputStream is = LicenseVerifier.class.getResourceAsStream("/public.key");
//            Throwable var3 = null;
//
//            try {
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                Streams.copy(is, out);
//                publicKeyBytes = out.toByteArray();
//            } catch (Throwable var13) {
//                var3 = var13;
//                throw var13;
//            } finally {
//                if (is != null) {
//                    if (var3 != null) {
//                        try {
//                            is.close();
//                        } catch (Throwable var12) {
//                            var3.addSuppressed(var12);
//                        }
//                    } else {
//                        is.close();
//                    }
//                }
//
//            }
//        } catch (IOException var15) {
//            throw new IllegalStateException(var15);
//        }
//
//        return verifyLicense(license, publicKeyBytes);

        return true;
    }
}
