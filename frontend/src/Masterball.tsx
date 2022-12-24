import './masterball.css';
import { motion } from "framer-motion"
import React from 'react';

interface Props {
    onOpen: Function;
}
export default function Masterball(props : Props) {
    const [endY, setEndY] = React.useState(0); //Hacer la animación con una libreria, la idea es que la pokebola se caiga de la pantalla, que se desmonte el componente y que se le pegue al back.
    const [rotation, setRotation] = React.useState(0);
    const [opacity, setOpacity] = React.useState(1);
    
    const openMasterball = () => {
        setEndY(1000);
        setRotation(360);

        setTimeout(disappear, 1500);
    }

    const disappear = () => {
        props.onOpen();
        setOpacity(0);
    }
    return (
    <motion.div className='no-background-color' animate={{ y: [0, endY], rotate: rotation}} transition={{duration: 2}}>
        <motion.section className='no-background-color' animate={{opacity: opacity}} transition={{duration: .5}}>
                <div className="one"></div>
                <div className="two"></div>
                <div className="four"></div>
                <div className="five"></div>
                <div className="six"></div>
                <div className="seven">.</div>
                <div className="eight">.</div>
                <div onClick={ openMasterball } className="nine">.</div>
                <h1 className="masterball-name">w</h1>
            <div className="three"></div>
        </motion.section>
    </motion.div>
    )
}