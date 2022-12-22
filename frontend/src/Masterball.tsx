import './masterball.css';
import React from 'react';

export default function Masterball() {
    const [show, setShow] = React.useState(true); //Hacer la animación con una libreria, la idea es que la pokebola se caiga de la pantalla, que se desmonte el componente y que se le pegue al back.
    const openMasterball = () => {
        setShow(false);
    }
    return (
        <section className={show ? 'show' : 'hide'}>
            <section>
                <div className="one"></div>
                <div className="two"></div>
                <div className="four"></div>
                <div className="five"></div>
                <div className="six"></div>
                <div className="seven">.</div>
                <div className="eight">.</div>
                <div onClick={ openMasterball } className="nine">.</div>
                <h1 className="masterball-name">w</h1>
            </section>
            <div className="three"></div>
        </section>
    )
}