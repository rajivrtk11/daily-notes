// To search for a value within a nested object in JavaScript, you can use a recursive function that traverses through the object's properties and checks for the desired value. Here's how you can implement it:

function findInNestedObject(obj, targetValue) {
    // Base case: if the current object is null or undefined, return false
    if (obj === null || typeof obj !== 'object') {
        return false;
    }
    
    // Check if the current object contains the target value
    if (Object.values(obj).includes(targetValue)) {
        return true;
    }
    
    // Recursively search through nested objects
    for (let key in obj) {
        if (findInNestedObject(obj[key], targetValue)) {
            return true;
        }
    }
    
    // If the target value is not found in the current object or its descendants, return false
    return false;
}

// Example usage:
const nestedObject = {
    a: 1,
    b: {
        c: 2,
        d: {
            e: 3,
            f: {
                g: 4
            }
        }
    }
};

console.log(findInNestedObject(nestedObject, 3)); // Output: true
console.log(findInNestedObject(nestedObject, 5)); // Output: false

/* 
    In this example:

    1. The findInNestedObject function takes an object (obj) and a target value (targetValue) to search for.
    2. It recursively traverses through the object's properties using a for...in loop.
    3. At each level, it checks if the current object contains the target value using Object.values(obj).includes(targetValue).
    4. If the target value is found in the current object or any of its nested objects, it returns true.
    5. If the target value is not found in the entire object hierarchy, it returns false.
    6. You can call findInNestedObject with any object and the value you want to search for within its nested structure.
*/

// =====================================================================================================

/*
    Deep clonning
    Deep cloning an object means creating a new object with the same structure and values as the original object, but with entirely new references. This ensures that changes made to the cloned object do not affect the original object, and vice versa. Here's how you can achieve deep cloning in JavaScript:
*/

console.log(Object.values(['a', 'b']))