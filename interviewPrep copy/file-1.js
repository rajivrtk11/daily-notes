// setImmediate -> Check handler/queue
// process.nextTick
// setTimeout -> timer queue

// function add(a, b) {
//     console.log(a+b);
// }

// setImmediate(() => {
//     add(2, 3);
//     console.log("This is Set Immediate");
// });

// setTimeout(() => { // higher priority than immediate
//     add(3, 5); 
//     console.log("This is Set Timeout");
// });

// process.nextTick(() => {
//     add(4, 5); 
//     console.log("This is Next Tick");
// })
// console.log("Program has ended.")

function debounce(func, delay) {
    let timeout;
    return function (...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), delay);
    };
}

// Function to simulate a search
const search = (query) => {
    console.log(`Searching for: ${query}`);
};

// Debounced version of the search function
const debouncedSearch = debounce(search, 500);

// Simulate typing in the terminal
const simulatedTyping = ["h", "he", "hel", "hell", "hello"];
simulatedTyping.forEach((query, index) => {
    setTimeout(() => debouncedSearch(query), index * 200); // Typing every 200ms
});


function throttle(func, interval) {
    let lastCall = 0;
    return function (...args) {
        const now = Date.now();
        if (now - lastCall >= interval) {
            lastCall = now;
            func.apply(this, args);
        }
    };
}

// Function to simulate scrolling
const onScroll = () => {
    console.log(`Scroll event triggered at: ${new Date().toISOString()}`);
};

// Throttled version of the scroll handler
const throttledScroll = throttle(onScroll, 1000);

// Simulate continuous scroll events
const simulateScroll = setInterval(() => throttledScroll(), 200); // Scroll event every 200ms

// Stop simulation after 5 seconds
setTimeout(() => clearInterval(simulateScroll), 5000);



