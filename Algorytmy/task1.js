function podzielna11(string) {
  const strLength = string.length;
  if (strLength === 1) {
    return string[0] === '0' ? 'TAK' : 'NIE';
  }
  let nieparzyste = 0;
  let parzyste = 0;
  for (let i = 1; i <= strLength; i++) {
    const digit = parseInt(string[i - 1]);
    if (i % 2 === 0) {
      parzyste += digit;
    } else {
      nieparzyste += digit;
    }
  }
  const newString = Math.abs(parzyste - nieparzyste).toString();
  return podzielna11(newString);
}
console.log(podzielna11("854073")); // TAK (854073 / 11 = 77643)
// console.log(podzielna11("123"));    // NIE
// console.log(podzielna11("0"));      // TAK
// console.log(podzielna11("121"));