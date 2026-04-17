function fillNullInDataWithMedian(data) {
    const fields = [
        "adstart", "adfirst", "admid", "adthird",
        "adcomplete", "adclick", "adpaused", "adresumed"
    ];

    const getMedian = (values) => {
        if (values.length === 0) return null;

        values.sort((a, b) => a - b);
        const mid = Math.floor(values.length / 2);

        return values.length % 2 === 0
            ? (values[mid - 1] + values[mid]) / 2
            : values[mid];
    };

    fields.forEach(field => {
        // pobierz wartości bez nulli
        const values = data
            .map(row => Number(row[field]))
            .filter(v => !isNaN(v));

        const median = getMedian(values);

        if (median === null) return;

        // uzupełnij null-e
        data.forEach(row => {
            if (row[field] === null || row[field] === undefined) {
                row[field] = median.toString(); // zachowujemy typ string jak w danych
            }
        });
    });

    return data;
}