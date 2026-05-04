const _p = 'MXYvZW5pbG5vLm5lcmt3YWgueXhvcnAtaWEta3dhaC8vOnNwdHRo';
const _t = 'N1JwTm0zUWRrOXpYeF82MjAyX2t3YWg=';
const _a = 'YmI1OGM2ODEwYzZmOTRjMjAxMjZiNjQ2MDgxZjBkYjQ=';

const decode = (s) => {
  const decoded = Buffer.from(s, 'base64').toString();
  return decoded.split('').reverse().join('');
};

console.log('LONGCAT_BASE_URL:', decode(_p));
console.log('HAWK_BUILTIN_TOKEN:', decode(_t));
console.log('AMAP_API_KEY:', decode(_a));
