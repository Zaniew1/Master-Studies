
// Bubble sort polega na przeiterowaniu po całej tablicy i porównaniu kazdego elementu z elementem następnym 
// jeśli następny jest mniejszy od elementu to zamieniamy ich kolejnością 
// następnie powtarzamy całą iterację 
// powtarzamy dopóki cała tablica nie zostania posortowana
// jeśli nie ma podmianki (noSwap) to przerywamy iterację bo to znaczy że tablica jest posortowana 
// oszczędza to nam dodatkowych iteracji

// BIG O NOTATION - O(n^2)
function bubbleSort(arr){
    for(let i = arr.length; i > 0; i--){
        let noSwap = true;
        for(let j =0; j < i-1; j++){
            if(arr[j] > arr[j+1]){
                const temporary = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temporary;
                noSwap = false;
            }
        }
        if(noSwap) break;
    }
    return arr;
}


const result = bubbleSort([1,8,2,33,21,17]);
console.log(result)