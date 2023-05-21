// import './index.css';
import React from 'react'
import ReactDOM from 'react-dom'
import App from './App'

// Opt-in to Webpack hot module replacement
if (module.hot) module.hot.accept()


ReactDOM.render(
    <App />,
    document.getElementById('app')
)


// import './index.css';
// import reportWebVitals from './reportWebVitals';
// import state from "./adapters/state";
// import {rerenderEntireTree} from "./adapters/render";
//
//
// rerenderEntireTree(state)
// reportWebVitals();