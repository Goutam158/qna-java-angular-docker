var express = require("express");
var app = express();
app.use(express.static(__dirname + '/dist'));
    app.get('/', function(req,res){
        res.staus(200).send('OK');
    });
app.listen(4200,function(request,response){

});
