/**
 * Map polyfill
 */
Array.prototype.myMap = function (callback) {
  const arr = this;
  const result = new Array(arr.length); // Preallocate result array of the same length

  // Iterate over the array, applying the callback to each item
  for (let i = 0; i < arr.length; i++) {
    if (i in arr) {
      // Check if the element is in the array (handles sparse arrays)
      result[i] = callback(arr[i], i, arr);
    }
  }

  return result;
};

// client
const arr = [1, 2, 3, 4];
// const ans = arr.myMap((ele) => ele*2);
// console.log('ans ', ans);

/**
 * Filter polyfill
 */
Array.prototype.myFilter = function (callback) {
  const arr = this;
  const ans = [];

  for (const ele in arr) {
    if (callback(ele)) {
      ans.push(ele);
    }
  }
  return ans;
};

// const filteredValue = arr.myFilter((ele) => ele%2 === 0);
// console.log("Filtered value ", filteredValue);

/**
 * Reduce polyfill
 */
Array.prototype.myReduce = function (callback, initialValue) {
  const arr = this;

  let accumulator = initialValue;

  for (let i = 0; i < arr.length; i++) {
    if (accumulator === undefined) {
      accumulator = arr[i];
    } else accumulator = callback(accumulator, arr[i]);
  }
  return accumulator;
};

// const reducedValue = arr.myReduce((acc, curr) => acc + curr, 0);
// console.log("reduced values ", reducedValue);

const personInfo = [
  {
    name: "Rajiv singh",
    age: 25,
  },
  {
    name: "Sambit nanda",
    age: 24,
  },
  {
    name: "Ankit roy",
    age: 27,
  },
  {
    name: "Harsimran singh",
    age: 24,
  },
];

const collectionByAge = personInfo.myReduce((acc, person) => {
  if (!acc[person.age]) {
    acc[person.age] = [person.name];
  } else acc[person.age].push(person.name);
  return acc;
}, {});

// console.log("CollectionByAge ", collectionByAge);

/**
 * 1. Write and explain closure.
 * 2. Write and explain currying.
 * 3. Write and explain your own use memo.
 * 4. Write and explain callback, promises and aysnc await.
 * 5. Write and explain pollyfills.
 *    a. map, filter, reduce.
 *    b. call, bind, apply.
 *    c. promises, setTimeout
 * 6. ....
 */

// 1. Write and explain closure.
const closure = () => {
  const name = "Rajiv singh";
  return () => {
    console.log("My name is", name);
  };
};

// const getName = closure();
// getName();

// 2. Write and explain currying.
const getMultiplication = (a, b) => {
  return a * b;
};

const getMultiplicationByCurrying = (a) => {
  return (b) => a * b;
};

// const multiplication = getMultiplicationByCurrying(10)(10);
// console.log('Multiplication ', multiplication);

// Currying for n arguement
const sum = (a) => {
  return function add(b) {
    if (!b) {
      return a;
    }

    return sum(a + b);
  };
};

// let val = sum(10)(20)(30)(40)();
// console.log("value is", val);

// 3. Write and explain your own use memo.
const useMemo = (fn) => {
  const cache = new Map();

  return (...args) => {
    const key = args.join("-");
    if (cache.has(key)) return cache.get(key);

    const ans = fn(args);
    cache.set(key, ans);
    return ans;
  };
};

const memoCallback = (args) => {
  return args.reduce((prev, curr) => prev + curr, 0);
};

// const memoResult1 = useMemo(memoCallback);
// const memoAns = memoResult1(1, 2, 3);
// console.log("Memo ans", memoAns);

// 4. Write and explain callback, promises and aysnc await.
// concatenate the string give to each function that execute after random amount of time
// const printString = (str) => {
//     setTimeout(() => {
//         console.log(str);
//     }, Math.random()*100);
// }

// const a = printString('a');
// printString('b');
// printString('c');

// const promise1 = Promise.resolve(3);
// const promise2 = 42;
// const promise3 = new Promise((resolve, reject) => {
//   setTimeout(resolve, 5000, 'foo 3');
// });
// const promise4 = new Promise((resolve, reject) => {
//     setTimeout(resolve, 100, 'foo');
//   });

// Promise.all([promise1, promise2, promise3, promise4]).then((values) => {
//   console.log(values);
// });

// Promise.allSettled([promise1, promise2, promise3, promise4]).then((result) => {
//     console.log(result);
//   });
// Expected output: Array [3, 42, "foo"]

/**
 * Promise pollyfill
 */

function MyPromise(executer) {
  let onResolve;
  let onReject;
  let isFullfilled = false;
  let isRejected = false;
  let isCalled = false;
  let value;
  let error;

  this.then = function (thenHandler) {
    onResolve = thenHandler;
    if (!isCalled && !isFullfilled) {
      setTimeout(() => {
        onResolve(value);
      }, 0);
      isCalled = true;
    }
    return this;
  };

  this.catch = function (catchHandler) {
    onReject = catchHandler;
    if (!isCalled && !isRejected) {
      onReject(error);
      isCalled = true;
    }
    return this;
  };

  function resolve(val) {
    value = val;
    if (typeof onResolve === "function" && !isCalled) {
      isFullfilled = true;
      onResolve(val);
      isCalled = true;
    }
  }

  function reject(err) {
    error = err;
    if (typeof onReject === "function" && !isCalled) {
      isRejected = true;
      onReject(err);
      isCalled = true;
    }
  }

  executer(resolve, reject);
}

// class MyPromise {
//   constructor(executor) {
//     this.state = "pending";
//     this.value = undefined;
//     this.handlers = [];

//     const resolve = (value) => {
//       if (this.state === "pending") {
//         this.state = "fulfilled";
//         this.value = value;
//         this.processHandlers();
//       }
//     };

//     const reject = (error) => {
//       if (this.state === "pending") {
//         this.state = "rejected";
//         this.value = error;
//         this.processHandlers();
//       }
//     };

//     try {
//       executor(resolve, reject);
//     } catch (err) {
//       reject(err);
//     }
//   }

//   processHandlers() {
//     if (this.state === "pending") return;
//     // setTimeout(() => {
//       this.handlers.forEach((handler) => {
//         if (this.state === "fulfilled") {
//           if (handler.onFulfilled) {
//             try {
//               const result = handler.onFulfilled(this.value);
//               handler.resolve(result);
//             } catch (err) {
//               handler.reject(err);
//             }
//           } else {
//             handler.resolve(this.value);
//           }
//         }
//         if (this.state === "rejected") {
//           if (handler.onRejected) {
//             try {
//               const result = handler.onRejected(this.value);
//               handler.resolve(result);
//             } catch (err) {
//               handler.reject(err);
//             }
//           } else {
//             handler.reject(this.value);
//           }
//         }
//       });
//       this.handlers = [];
//     // }, 0);
//   }

//   then(onFulfilled, onRejected) {
//     return new MyPromise((resolve, reject) => {
//       this.handlers.push({
//         onFulfilled,
//         onRejected,
//         resolve,
//         reject,
//       });
//       this.processHandlers();
//     });
//   }

//   catch(onRejected) {
//     return this.then(null, onRejected);
//   }

//   finally(onFinally) {
//     return this.then(
//       (value) => {
//         onFinally();
//         return value;
//       },
//       (reason) => {
//         onFinally();
//         throw reason;
//       }
//     );
//   }

//   static resolve(value) {
//     return new MyPromise((resolve) => resolve(value));
//   }

//   static reject(reason) {
//     return new MyPromise((_, reject) => reject(reason));
//   }

//   static all(promises) {
//     return new MyPromise((resolve, reject) => {
//       let fulfilledCount = 0;
//       const results = new Array(promises.length);

//       promises.forEach((promise, index) => {
//         MyPromise.resolve(promise)
//           .then((value) => {
//             results[index] = value;
//             fulfilledCount += 1;
//             if (fulfilledCount === promises.length) {
//               resolve(results);
//             }
//           })
//           .catch(reject);
//       });
//     });
//   }

//   static race(promises) {
//     return new MyPromise((resolve, reject) => {
//       promises.forEach((promise) => {
//         MyPromise.resolve(promise).then(resolve).catch(reject);
//       });
//     });
//   }
// }


  
// console.log("line 1");
// const p1 = new MyPromise((resolve, reject) => {
//   resolve(12);
// });
// p1.then((res) => {
//   console.log("val ", res);
// });
// console.log("line 2");

// console.log('line 1');
// requestIdleCallback(() => console.log('line 2'));
// console.log('line 3');

requestIdleCallback()
