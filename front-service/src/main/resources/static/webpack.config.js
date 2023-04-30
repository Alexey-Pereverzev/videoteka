const path = require("path");
var packageJSON = require('./package.json');
var webpack = require('webpack');

module.exports = {
    mode: "development",
    entry: "./index.js", // входная точка - исходный файл
    output:{
        path: path.resolve(__dirname, 'generated'),     // путь к каталогу выходных файлов
        publicPath: "/generated/",
        filename: "app-bundle.js"       // название создаваемого файла
    },
    resolve: {extensions: ['.js', '.jsx']},
    devServer: {
        noInfo: false,
        quiet: false,
        lazy: false,
        watchOptions: {
            poll: true
        }
    },
    module:{
        rules:[   //загрузчик для jsx
            {
                test: /\.jsx?$/, // определяем тип файлов
                exclude: /(node_modules)/,  // исключаем из обработки папку node_modules
                loader: "babel-loader",   // определяем загрузчик
                options:{
                    presets:[ "@babel/preset-react"]    // используемые плагины
                }
            }
        ]
    }
}