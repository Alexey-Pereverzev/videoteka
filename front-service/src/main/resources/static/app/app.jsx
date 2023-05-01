const ReactDOM = require("react-dom/client");
const React = require("react");
const Header = require("./components/header.jsx");
const Article = require("./components/article.jsx");

const header = "ФЕДЯ!";
const article = "Если ты читаешь этот текст, то я запустил реакт через спринг бут!";

ReactDOM.createRoot(
    document.getElementById("app")
)
    .render(
        <div>
            <Header text={header} />
            <Article content={article} />
        </div>
    );