function sumOfThreeElements(...elements) {
    return new Promise((resolve, reject) => {
        if (elements.length > 3) {
            reject("Only three elements or less are allowed");
        }
        else {
            let sum = 0;
            let i = 0;
            while (i < elements.length) {
                sum += elements[i];
                i++;
            }
            console.log('inside function');
            resolve("Sum has been calculated: " + sum);
        }
    })
}
  
// console.log('line 1');
// sumOfThreeElements(4, 5, 6)
// .then(result=> console.log(result))
// .catch(error=> console.log(error));
// console.log('line 2');


const checkAsyncFunctionBehaviour = async() => {
    console.log('line 1');
    const res = await sumOfThreeElements(4, 5, 6);
    // console.log('the res', res);
    console.log('line 2');
}
// checkAsyncFunctionBehaviour();
// In the code above, the promise is fulfilled so the then() method gets executed

// sumOfThreeElements(7, 0, 33, 41)
// .then(result => console.log(result))
// .catch(error=> console.log(error));

function* iteratorFunc() {
    let count = 0;
    for (let i = 0; i < 2; i++) {
        count++;
        yield i;
    }
    yield count;
}

// let iterator = iteratorFunc();
// console.log(iterator.next()); // {value:0,done:false}
// console.log(iterator.next()); // {value:1,done:false}
// console.log(iterator.next()); // {value:2,done:true}

/**
 * Weak set question.
 */

// const newSet = new Set([4, 5, 6, 7]);
// console.log(newSet);// Outputs Set {4,5,6,7}

// const newSet2 = new WeakSet([3, 4, 5]); //Throws an error


// let obj1 = {message:"Hello world"};
// const newSet3 = new WeakSet([obj1]);
// console.log(newSet3.has(obj1)); // true

/**
 * Scoping question
 */
  
// var scope = "global scope";
// function check() 
// {
//     var scope = "local scope"; 
//     function f() 
//     { 
//          return scope; 
//     }
//     return f;
// }
// const res = check()();
// console.log('the res ', res);

/**
 * this keyword
 */

const obj = {
    name: 'Rajiv Singh',
    getName: () => {
        const nestedObj = {
            name: 'nest Rajiv', 
            getName: () => this,
        }
        return nestedObj.getName();
    },
    getNameByNormalFunction: function() {
        return this;
    }
}

// const getNameByNormalFunction = obj.getNameByNormalFunction();
// const nameByArrowFn = obj.getName();
// const nestedObj = obj.getName();
// console.log('nested name', nestedObj);
// console.log('name ', nameByArrowFn);

/**
 * Big int
 */

var bigInteger =  23456789012345678901234567890123456789011111111111111111132323232323232323232323n
// console.log('big int ', (bigInteger+'d').length, bigInteger);

// let val = NaN;
// console.log(typeof val);


// const newSet2 = new WeakSet([3, 4, 5]); //Throws an error


let obj1 = {message:"Hello world"};
const newSet3 = new WeakSet([{message:"Hello world"}, {message:"Hello world"}]);
console.log(newSet3.has(obj1)); // true


