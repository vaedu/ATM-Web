// src/assets/scripts/encryption.js
import CryptoJS from "crypto-js";

// ⭐ 必须是 16 字符（AES-128）你可以更换成自己的密钥，但必须保持与后端完全一致！
const SECRET_KEY = "your-secret-key!"; // 16 chars

export function encryptPassword(password) {
    const key = CryptoJS.enc.Utf8.parse(SECRET_KEY);

    const encrypted = CryptoJS.AES.encrypt(
        CryptoJS.enc.Utf8.parse(password),
        key,
        {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        }
    );

    return encrypted.toString(); // Base64
}
