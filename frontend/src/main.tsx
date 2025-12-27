import React from 'react'
import ReactDOM from 'react-dom/client'
import FrontendApp from "./FrontendApp.tsx";
import './style.css'

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <FrontendApp />
    </React.StrictMode>,
)