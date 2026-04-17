
// INSERTION SORT polega na przeiterowaniu po każdym elemencie tablicy
// wstawieniu elementu na jego odpowiednie miejsce jeśli był mniejszy niż jego poprzednik
// jesli element jest większy niż poprzednik to zostaje w miejscu
// powoduje to że lewa część tablicy jest od razu posortowana a prawa(nieposortowana) pomału się zmniejsza

// sposób na posortowanie:
// 1. Weź 2 element tablicy
// 2. porównaj do pierwszego elementu i zamień jeśli jest taka potrzeba
// 3. Zrób to samo z kolenym elementem i porównuj do [element-1]
// 4. Jeśli element jest mniejszy to przeiteruj po posortowanej części i wstaw w odpowiednie miejsce
// 5. Jesli element jest większy niż poprzednik to zostaw w miejscu i przejdz do kolejnego elementu
// 6. Powtarzaj aż posortujesz całą tablicę

// BIG O NOTATION - O(n^2)

function insertionSort(arr){
    for(let i = 1; i < arr.length; i++){
        let currentVal = arr[i];
        for(let j= i-1; j >= 0 && arr[j] > currentVal; j--){
            arr[j+1] = arr[j];
        }
        arr[j+1] =currentVal;
    }


    return arr;
}

const result = insertionSort([1,8,2,33,21,17]);
console.log(result)