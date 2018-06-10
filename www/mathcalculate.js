var exec = require('cordova/exec');

exports.setupJivoSDK = function (arg0, success, error) {
    exec(success, error, 'mathcalculate', 'setupJivoSDK', [arg0]);
};

exports.startChat = function (arg0, success, error) {
    exec(success, error, 'mathcalculate', 'startChat', [arg0]);
};

exports.startWithWebChat = function (arg0, success, error) {
    exec(success, error, 'mathcalculate', 'startWithWebChat', [arg0]);
};
