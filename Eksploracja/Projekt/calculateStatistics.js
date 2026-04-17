function getNumericValues(data, field) {
    return data
        .map(row => Number(row[field]))
        .filter(v => !isNaN(v));
}

// ŚREDNIA Arytmetyczna
function calculateAverage(data, field) {
    const values = getNumericValues(data, field);
    if (values.length === 0) return null;

    const sum = values.reduce((a, b) => a + b, 0);
    return sum / values.length;
}

// mediana 
function calculateMedian(data, field) {
    const values = getNumericValues(data, field).sort((a, b) => a - b);
    const n = values.length;

    if (n === 0) return null;

    const mid = Math.floor(n / 2);

    return n % 2 === 0
        ? (values[mid - 1] + values[mid]) / 2
        : values[mid];
}

function calculateMin(data, field) {
    const values = getNumericValues(data, field);
    return values.length ? Math.min(...values) : null;
}

function calculateMax(data, field) {
    const values = getNumericValues(data, field);
    return values.length ? Math.max(...values) : null;
}

// Odchylenie standardowe
function calculateStandardDeviation(data, field) {
    const values = getNumericValues(data, field);
    const n = values.length;

    if (n === 0) return null;

    const mean = calculateAverage(data, field);

    const variance = values.reduce((sum, val) => {
        return sum + Math.pow(val - mean, 2);
    }, 0) / n;

    return Math.sqrt(variance);
}

// kwartyle
function calculateQuartiles(data, field) {
    const values = getNumericValues(data, field).sort((a, b) => a - b);
    const n = values.length;

    if (n === 0) return { Q1: null, Q2: null, Q3: null };

    const median = (arr) => {
        const m = Math.floor(arr.length / 2);
        return arr.length % 2 === 0
            ? (arr[m - 1] + arr[m]) / 2
            : arr[m];
    };

    const Q2 = median(values);

    const lowerHalf = values.slice(0, Math.floor(n / 2));
    const upperHalf = values.slice(Math.ceil(n / 2));

    const Q1 = median(lowerHalf);
    const Q3 = median(upperHalf);

    return { Q1, Q2, Q3 };
}

function calculateKurtosis(data, field) {
    const values = getNumericValues(data, field);
    const n = values.length;
    if (n === 0) return null;

    const mean = values.reduce((a, b) => a + b, 0) / n;

    const std = Math.sqrt(
        values.reduce((sum, v) => sum + Math.pow(v - mean, 2), 0) / n
    );

    if (std === 0) return 0;

    const kurtosis = values.reduce((sum, v) => {
        return sum + Math.pow((v - mean) / std, 4);
    }, 0) / n;

    return kurtosis - 3; // nadmiarowa kurtoza
}

function calculateSkewness(data, field) {
    const values = getNumericValues(data, field);
    const n = values.length;
    if (n === 0) return null;

    const mean = values.reduce((a, b) => a + b, 0) / n;

    const std = Math.sqrt(
        values.reduce((sum, v) => sum + Math.pow(v - mean, 2), 0) / n
    );

    if (std === 0) return 0;

    const skewness = values.reduce((sum, v) => {
        return sum + Math.pow((v - mean) / std, 3);
    }, 0) / n;

    return skewness;
}

function calculateIQR(data, field) {
    const values = getNumericValues(data, field).sort((a, b) => a - b);
    const n = values.length;
    if (n === 0) return null;

    const median = (arr) => {
        const m = Math.floor(arr.length / 2);
        return arr.length % 2 === 0
            ? (arr[m - 1] + arr[m]) / 2
            : arr[m];
    };

    const lower = values.slice(0, Math.floor(n / 2));
    const upper = values.slice(Math.ceil(n / 2));

    const Q1 = median(lower);
    const Q3 = median(upper);

    return Q3 - Q1;
}

function calculateVariance(data, field) {
    const values = getNumericValues(data, field);
    const n = values.length;
    if (n === 0) return null;

    const mean = values.reduce((a, b) => a + b, 0) / n;

    const variance = values.reduce((sum, val) => {
        return sum + Math.pow(val - mean, 2);
    }, 0) / n;

    return variance;
}


console.log("Adstart Variance:", calculateVariance(data, "adstart"));
console.log("Adstart Std Dev:", calculateStandardDeviation(data, "adstart"));
console.log("Adstart IQR:", calculateIQR(data, "adstart"));
console.log("Adstart Skewness:", calculateSkewness(data, "adstart"));
console.log("Adstart Kurtosis:", calculateKurtosis(data, "adstart"));