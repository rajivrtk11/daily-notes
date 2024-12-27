// process.env.UV_THREADPOOL_SIZE = 2;
const crypto = require('crypto');
const https = require('https');

// can you write a func for n level currying...
const sum = (a) => {

  return function add(b) {
    if (!b) {
      return a;
    }

    return sum(a + b);
  };
};

// let val = sum(10)(20)(30)(40)()
// console.log('value is', val);

// const obj = {
//     name: 12,
//     func(){
//       setTimeout(function innerFun() {
//             console.log('this is', this);
//       }, 0);
//     }
// }

// obj.func();
// we cann't modify context of this with with arrow function for call, apply and bind
// we can set the context of this at runtime

const getName = function () {
//   console.log("Name is", this.name);
};

// const obj = {
//     name: "Rajiv",
// }

// getName.apply(obj);

const add = (a, b) => a + b;

// can you write a func or pollyfill for memo
const memo = (fn) => {
  const cache = new Map();

  return function (...args) {
    const key = args.join("-");
    if (cache.has(key)) return cache.get(key);

    const result = fn(args);
    cache.set(key, result);

    return result;
  };
};

// const calculatedValue = memo(add);
// calculatedValue(a, b);

/**
 * Object methods
 */

// Object.assign
const obj1 = {
  name: "Rajiv",
  age: "25",
};

const obj2 = Object.assign(obj1, { mob: "972222" });
// console.log('Obj2 ', obj2);

// Object.fromEntries
const entriesObject = Object.fromEntries([
  ["name", "Rajiv"],
  ["age", "25"],
  ["mob", "972999"],
]);
// console.log('Object from entries ', entriesObject);

// Object getter and setter
//Create an object
const person = { firstName: "John", lastName: "Doe",
    get lang() {
        return this.lastName;
    }
 };

// Define a Getter
Object.defineProperty(person, "fullName", {
  get: function () {
    return this.firstName + " " + this.lastName;
  },
});
// console.log("Full name ", person.fullName);s

// Example-2
// Define object
const obj = { counter: 0 };

// Define setters
Object.defineProperty(obj, "reset", {
  get: function () {
    this.counter = 0;
  },
});

Object.defineProperty(obj, "increment", {
  get: function () {
    this.counter++;
  },
});

Object.defineProperty(obj, "decrement", {
  get: function () {
    this.counter--;
  },
});

Object.defineProperty(obj, "add", {
  set: function (value) {
    this.counter += value;
  },
});

Object.defineProperty(obj, "subtract", {
  set: function (i) {
    this.counter -= i;
  },
});

// Play with the counter:
obj.reset;
obj.add = 5;
// console.log('obj ', obj.counter);
obj.subtract = 1;
// console.log('obj ', obj.counter);
obj.increment;
// console.log('obj ', obj.counter);
obj.decrement;
// console.log('obj ', obj.counter);

// property management
const personInfo = {
    lastName: 'singh',
    getName() {
        // console.log('my name is ', this.name);
    }
};
Object.defineProperty(personInfo, 'name', {
    value: 'Rajiv',
    writable: true,
    configurable: true,
});
personInfo.name = 'sachin'
// console.log('personInfo ', personInfo.name);
// console.log('personal info ', personInfo);


  
// Set the language Property not enumerable
Object.defineProperty(person, "language", {enumerable:false});

// Get all Properties
// console.log(Object.keys(person));
// console.log(person.lang)

const ans = new Number(1212);
ans.a = 'rajiv'
// console.log('ans ', ans.a);

Function.prototype.myCall = function(context, ...args) {
    let attachFnName = Math.random()*100;
    
    while(context[attachFnName] !== undefined) {
        attachFnName = Math.random()*100;
    }
    context[attachFnName] = this;
    const result = context[attachFnName](...args);
    delete context[attachFnName];
    
    return result
}

function sayHello(age, color, mobile) {
    return "Hello " + this.name + " "+ age + " " + color+ ", "+ mobile;
}

let str = 'Rajiv singh';
function* rajiv() {
    yield 1;
    yield 2;
    yield 3;
}

let itr = rajiv();
// console.log('first ', itr);
// console.log('second ', itr.next().value);
// console.log('third ', itr.next().value);
// console.log('fourth ', itr.next().value);
// console.log('fifth ', itr.next().value);

var scope = "global scope";
function check() 
{
    var scope = "local scope"; 
    function f() 
    { 
         return scope; 
    }
    return f;
}

const outerLexical = check();
const innerLexical = outerLexical();
// console.log('value is ', innerLexical);

const start = Date.now();

const promiseFn = () => {
  return new Promise((resolve, reject) => {
    resolve(12);
  })
}
// console.log('line 1');
// promiseFn().then((res) => console.log("1: ", Date.now() - start));
// setTimeout(() => {
//   console.log('time out 1 ', Date.now() - start);
// }, 1000);
// setTimeout(() => {
//   console.log('time out 2 ', Date.now() - start);
// }, 1000);
// setTimeout(() => {
//   console.log('time out 3 ', Date.now() - start);
// }, 1000);
// setTimeout(() => {
//   console.log('time out 4 ', Date.now() - start);
// }, 1000);
// setTimeout(() => {
//   console.log('time out 5 ', Date.now() - start);
// }, 1000);
// setTimeout(() => {
//   console.log('time out 6 ', Date.now() - start);
// }, 1000);

// console.log('line 3');
// console.log('line 4');
// console.log('line 5');

// crypto.pbkdf2('a', 'b', 100000, 512, 'sha512', () => {
//   console.log('1: ', Date.now() - start);
// })
// crypto.pbkdf2('a', 'b', 100000, 512, 'sha512', () => {
//   console.log('2: ', Date.now() - start);
// })
// crypto.pbkdf2('a', 'b', 100000, 512, 'sha512', () => {
//   console.log('3: ', Date.now() - start);
// })
// crypto.pbkdf2('a', 'b', 100000, 512, 'sha512', () => {
//   console.log('4: ', Date.now() - start);
// })
// crypto.pbkdf2('a', 'b', 100000, 512, 'sha512', () => {
//   console.log('5: ', Date.now() - start);
// })

function doRequest() {
  https.request('https://www.google.com', res => {
    res.on('data', () => {});
    res.on('end', () => {
        console.log('time: ', Date.now() - start);
    })
  })
  .end();
}

doRequest();
doRequest();
doRequest();
doRequest();
doRequest();
doRequest();

fetch('https://www.google.com').then(res => {
  console.log('res: ', res);
  return res.json()
})
.then(data => console.log('data: ', data));