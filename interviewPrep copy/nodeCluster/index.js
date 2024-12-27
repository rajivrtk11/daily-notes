const cluster = require('cluster');

console.log('is master node ', cluster.isMaster);
function doWork(duration) {
  const start = Date.now();
  while (Date.now() - start < duration) {}
}

if(cluster.isMaster) {
    cluster.fork();
}
else {
    const express = require("express");
    const app = express();
    const port = 3000;
    app.get("/", (req, res) => {
      doWork(5000);
      res.send("Hello World !");
    });

    app.get("/fast", (req, res) => {
        doWork(5000);
        res.send("Hello World fast !");
      });
    
    app.listen(port, () => {
      console.log(`Example app listening on port ${port}`);
    });
}

