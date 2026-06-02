function countTriangleColorings(k) {
  if (k < 0) return 0;
  return (Math.pow(k, 3) + 2 * k) / 3;
}