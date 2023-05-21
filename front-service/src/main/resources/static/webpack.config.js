const path = require("path");
const webpack = require('webpack');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
    resolve: {
        alias: {
            components: path.resolve(__dirname, 'src/components/'),
        },
        extensions: ['.js', '.jsx'],
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: '[name].bundle.css',
            chunkFilename: '[id].css'
        }),
        new webpack.HotModuleReplacementPlugin()
    ],
    mode: "development",
    entry: "./src/index.js", // входная точка - исходный файл
    output:{
        path: path.resolve(__dirname, "./public"),     // путь к каталогу выходных файлов - папка public
        publicPath: "/public/",
        filename: "bundle.js"       // название создаваемого файла
    },
    devServer: {
        historyApiFallback: true,
        static: {
            directory: path.join(__dirname, "/"),
        },
        port: 8081,
        open: true,
        hot: true
    },
    module:{
        rules:[   //загрузчик для jsx
            {
                test: /\.(jsx|js)?$/, // определяем тип файлов
                exclude: /(node_modules)/,  // исключаем из обработки папку node_modules
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            ['@babel/preset-env', {
                                "targets": "defaults"
                            }],
                            '@babel/preset-react'
                        ]
                    }
                }]
            },
            {
                test: /\.css$/i,
                exclude: /node_modules/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {
                            // hmr: env.NODE_ENV === 'dev',
                        }
                    },
                    {
                        loader: 'css-loader',
                        options: {
                            importLoaders: 0
                        }
                    }
                    // ,
                ]
            }
            // ,
            // {
            //     test: /\.css$/,
            //     // use: ['style-loader', 'css-loader']
            // }
        ]
    }
}