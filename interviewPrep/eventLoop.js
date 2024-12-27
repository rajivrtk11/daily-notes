const https = require("https");
const crypto = require("crypto");
const fs = require("fs");

const start = Date.now();

function hashing() {
  crypto.pbkdf2("a", "b", 100000, 512, "sha512", () => {
    console.log("5: ", Date.now() - start);
  });
}

function fileRead() {
  fs.readFile("eventLoop.js", (e, data) => {
    // console.log("data ", data.toString());
    console.log("FS: ", Date.now() - start);
  });
}

function doRequest() {
  https
    .request("https://www.google.com", (res) => {
      res.on("data", () => {});
      res.on("end", () => {
        console.log("time: ", Date.now() - start);
      });
    })
    .end();
}

doRequest();
fileRead();
hashing();
hashing();
hashing();
hashing();

