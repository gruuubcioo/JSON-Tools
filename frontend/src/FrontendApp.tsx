import {transformJsonBeautify, transformJsonFilter, transformJsonMinify, compareJson} from './apiService.ts';
import './style.css';
import React, {useState} from "react";

type mode = 'minify' | 'filter' | 'compare' | 'beautify';

const FrontendApp: React.FC = () => {
    const [json1, setJson1] = useState<string>("");
    const [json2, setJson2] = useState<string>("");
    const [keys, setKeys] = useState<string>("");
    const [state, setState] = useState<mode>("minify");
    const [outputValue, setOutputValue] = useState<string>("");

    const handleSend = async () => {
        try {
            let result: string = "";
            if (state === 'minify') {
                result = await transformJsonMinify(json1);
            }
            if (state === 'beautify') {
                result = await transformJsonBeautify(json1);
            }
            if (state === 'filter') {
                const keysArray = keys.split(',').map(k => k.trim());
                result = await transformJsonFilter(keysArray, json1);
            }
            if (state === 'compare') {
                result = await compareJson(json1, json2);
            }

            setOutputValue(result);
        }
        catch(error){
            console.log(error);
            setOutputValue("Błąd: Nie udało się połączyć z serwerem lub błędny JSON.");
        }
    }

    return (
        <div className="container">
            <h1 className="main-title">JSON Tools</h1>

            <div className="options">
                {(['minify', 'filter', 'compare', 'beautify'] as mode[]).map((m) => (
                    <div
                        key={m}
                        className={`function ${state === m ? 'active' : ''}`}
                        onClick={() => setState(m)}
                    >
                        {m}
                    </div>
                ))}
            </div>

            <div className="work-area">
                <div className="inputs-row">
                    {state === 'filter' && (
                        <div className="input-group keys-input">
                            <label>Keys (comma separated):</label>
                            <input
                                value={keys}
                                onChange={(e) => setKeys(e.target.value)}
                                placeholder="id, name, data"
                            />
                        </div>
                    )}

                    <div className="input-group">
                        <label>{state === 'compare' ? 'JSON 1' : 'Input JSON'}:</label>
                        <textarea
                            value={json1}
                            onChange={(e) => setJson1(e.target.value)}
                            placeholder='{"key": "value"}'
                        />
                    </div>

                    {state === 'compare' && (
                        <div className="input-group">
                            <label>JSON 2:</label>
                            <textarea
                                value={json2}
                                onChange={(e) => setJson2(e.target.value)}
                                placeholder='{"key": "different"}'
                            />
                        </div>
                    )}
                </div>

                <button className="send-btn" onClick={handleSend}>
                    SEND
                </button>

                <div className="output-section">
                    <label>Result:</label>
                    <pre className="output-box">{outputValue}</pre>
                </div>
            </div>
        </div>
    )
}

export default FrontendApp;