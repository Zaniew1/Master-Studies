
// SELECTION SORT polega na przeiterowaniu całej tablicy a następnie
// na znalezieniu najniższej wartości w tablicy 
// a następnie umieszczeniu tej wartości na początku tablicy 
// aby unikąć infinite loopa trzeba co iterację zmniejszać wielkość tablicy po której iterujemy

// BIG O NOTATION - O(n^2)



function selectionSort(arr){
    for(let i =0; i < arr.length; i++){
        let minimum = i;
        for(let j = i+1; j< arr.length; j++){
            if(arr[j] < arr[minimum] ){
                minimum = j
            }
        }
        if(i !== minimum){
            const temp = arr[i];
            arr[i] = arr[minimum];
            arr[minimum] = temp;
        }
    }
    return arr;
}

const result = selectionSort([1,8,2,33,21,17]);
console.log(result)