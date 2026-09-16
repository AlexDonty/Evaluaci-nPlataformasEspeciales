const IV_LENGTH_BYTE = 12;
const TAG_LENGTH_BIT = 128;

export async function encryptAES(
  text: string,
  secretKey: string
): Promise<string> {

  // La clave que recibes está codificada en Base64
  const keyBytes = Uint8Array.from(
    atob(secretKey),
    char => char.charCodeAt(0)
  );

  // AES-256 necesita exactamente 32 bytes
  if (keyBytes.length !== 32) {
    throw new Error(
      `La clave AES-256 debe tener 32 bytes. Actual: ${keyBytes.length}`
    );
  }

  // Crear la clave AES
  const key = await crypto.subtle.importKey(
    "raw",
    keyBytes,
    {
      name: "AES-GCM"
    },
    false,
    ["encrypt"]
  );

  // Generar IV aleatorio de 12 bytes
  const iv = crypto.getRandomValues(
    new Uint8Array(IV_LENGTH_BYTE)
  );

  // Convertir texto a bytes UTF-8
  const data = new TextEncoder().encode(text);

  // Cifrar
  const encryptedBuffer = await crypto.subtle.encrypt(
    {
      name: "AES-GCM",
      iv: iv,
      tagLength: TAG_LENGTH_BIT
    },
    key,
    data
  );

  const encryptedBytes = new Uint8Array(encryptedBuffer);

  // IV + ciphertext + TAG
  const result = new Uint8Array(
    iv.length + encryptedBytes.length
  );

  result.set(iv, 0);
  result.set(encryptedBytes, iv.length);

  // Convertir todo a Base64
  return bytesToBase64(result);
}

function bytesToBase64(bytes: Uint8Array): string {
  let binary = "";

  bytes.forEach(byte => {
    binary += String.fromCharCode(byte);
  });

  return btoa(binary);
}