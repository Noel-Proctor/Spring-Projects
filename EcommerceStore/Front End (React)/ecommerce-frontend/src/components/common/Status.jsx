

function Status({ text, icon: Icon, style, size }) {

    return (
        <div className={style}>
            <p className="mr-1">{text}</p> <Icon size={20} ></Icon>
        </div>
    )
}


export default Status; 