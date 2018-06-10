var exec = require('cordova/exec');

exports.setupJivoSDK = function (arg0, success, error) {
    exec(success, error, 'mathcalculate', 'setupJivoSDK', [arg0]);
};
