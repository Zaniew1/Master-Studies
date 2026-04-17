function introduceNoise(data, missingPercent = 0.05, outlierPercent = 0.05) {
    const result = JSON.parse(JSON.stringify(data)); // deep copy

    const fields = [
        "adstart", "adfirst", "admid", "adthird",
        "adcomplete", "adclick", "adpaused", "adresumed"
    ];

    const totalValues = result.length * fields.length;

    const missingCount = Math.floor(totalValues * missingPercent);
    const outlierCount = Math.floor(totalValues * outlierPercent);

    // helper do losowego indeksu
    const getRandomIndex = (max) => Math.floor(Math.random() * max);

    // --- 1. WPROWADZANIE BRAKÓW ---
    for (let i = 0; i < missingCount; i++) {
        const rowIndex = getRandomIndex(result.length);
        const field = fields[getRandomIndex(fields.length)];

        result[rowIndex][field] = null;
    }

    // --- 2. WPROWADZANIE ODSTAJĄCYCH ---
    for (let i = 0; i < outlierCount; i++) {
        const rowIndex = getRandomIndex(result.length);
        const field = fields[getRandomIndex(fields.length)];

        let value = result[rowIndex][field];

        if (value !== null) {
            value = Number(value);

            if (!isNaN(value)) {
                // losowo: duży albo bardzo mały
                const isHigh = Math.random() > 0.5;

                if (isHigh) {
                    value = value * (5 + Math.random() * 5); // x5–x10
                } else {
                    value = value * Math.random() * 0.2; // blisko 0
                }

                result[rowIndex][field] = value.toFixed(2);
            }
        }
    }

    return result;
}
