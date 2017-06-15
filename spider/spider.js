var express = require('express');
var app = express();
var request = require('request');
var cheerio = require('cheerio');
var fs = require('fs');
var base1 = "https://guides.emberjs.com/v2.13.0/getting-started/quick-start/";
var base2 = "https://guides.emberjs.com/v2.13.0/getting-started/";
var base3 = "https://guides.emberjs.com/v2.13.0/";

app.get('/EmberTutorial', function(req, res) {
    fs.readFile("./sitesdata.html", 'utf-8', function(err, data) {
        var $ = cheerio.load(data);
        var sitesData = [];
        $('a').each(function(i, e) {
            var href = e.attribs.href;
            var regExp1 = /^..\/..\//;
            var regExp2 = /^..\//;
            if (href != '#') {
                if (regExp1.test(href)) {
                    href = (base3 + href.slice(6));
                } else if (regExp2.test(href)) {
                    href = (base2 + href.slice(3));
                } else {
                    href = (base1 + href.slice(2));
                }
                sitesData.push(href);
            }
        })
        sitesData.forEach(function(e, i) {
            var url = e;
            request(url, function(error, res, body) {
                if (!error && res.statusCode == 200) {
                    var $ = cheerio.load(body);
                    fs.appendFile('data.txt', $('article').html(), (err) => {
                        if (err) throw err;
                        console.log('The "data to append" was appended to file!');
                    });
                }
            })
        });
    })
    res.send('<a download="data.txt" href="data.txt">download</a>');
}).listen(3000, function() {
    console.log("listening at port 3000")
})
