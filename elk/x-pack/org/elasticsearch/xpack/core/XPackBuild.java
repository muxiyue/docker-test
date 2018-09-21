//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.elasticsearch.xpack.core;

import org.elasticsearch.common.SuppressForbidden;
import org.elasticsearch.common.io.PathUtils;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class XPackBuild {
    public static final XPackBuild CURRENT;
    private String shortHash;
    private String date;

    @SuppressForbidden(
        reason = "looks up path of xpack.jar directly"
    )
    static Path getElasticsearchCodebase() {
        URL url = XPackBuild.class.getProtectionDomain().getCodeSource().getLocation();

        try {
            return PathUtils.get(url.toURI());
        } catch (URISyntaxException var2) {
            throw new RuntimeException(var2);
        }
    }

    XPackBuild(String shortHash, String date) {
        this.shortHash = shortHash;
        this.date = date;
    }

    public String shortHash() {
        return this.shortHash;
    }

    public String date() {
        return this.date;
    }

    static {
        Path path = getElasticsearchCodebase();
        String shortHash;
        String date;
//        if (path.toString().endsWith(".jar")) {
//            try {
//                JarInputStream jar = new JarInputStream(Files.newInputStream(path));
//                Throwable var4 = null;
//
//                try {
//                    Manifest manifest = jar.getManifest();
//                    shortHash = manifest.getMainAttributes().getValue("Change");
//                    date = manifest.getMainAttributes().getValue("Build-Date");
//                } catch (Throwable var14) {
//                    var4 = var14;
//                    throw var14;
//                } finally {
//                    if (var4 != null) {
//                        try {
//                            jar.close();
//                        } catch (Throwable var13) {
//                            var4.addSuppressed(var13);
//                        }
//                    } else {
//                        jar.close();
//                    }
//
//                }
//            } catch (IOException var16) {
//                throw new RuntimeException(var16);
//            }
//        } else {
//            shortHash = "Unknown";
//            date = "Unknown";
//        }

//        Label_0157: {
            shortHash = "Unknown";
            date = "Unknown";
//        }

        CURRENT = new XPackBuild(shortHash, date);
    }
}
