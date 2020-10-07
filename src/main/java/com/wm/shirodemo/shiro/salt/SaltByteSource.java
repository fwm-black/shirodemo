package com.wm.shirodemo.shiro.salt;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 自定义salt实现序列化接口（盐无法通过ByteSource，在redis内进行序列化）
 *
 * 此处代码实现了ByteSource接口，其内部实现基本为 org.apache.shiro.util.SimpleByteSource 内的代码
 * 仅仅增加了无参构造，去掉了变量bytes的修饰符final，和实现了序列化接口
 *
 */
public class SaltByteSource implements ByteSource,Serializable {
    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public SaltByteSource(){

    }

    public SaltByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public SaltByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public SaltByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public SaltByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }

    public SaltByteSource(File file) {
        this.bytes = (new SaltByteSource.BytesHelper()).getBytes(file);
    }

    public SaltByteSource(InputStream stream) {
        this.bytes = (new SaltByteSource.BytesHelper()).getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }

        return this.cachedHex;
    }

    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }

        return this.cachedBase64;
    }

    public String toString() {
        return this.toBase64();
    }

    public int hashCode() {
        return this.bytes != null && this.bytes.length != 0 ? Arrays.hashCode(this.bytes) : 0;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ByteSource) {
            ByteSource bs = (ByteSource)o;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }
}
