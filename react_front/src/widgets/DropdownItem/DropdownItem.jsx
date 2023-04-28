import React from "react";
import {NavLink} from "react-router-dom";

import style from "./DropdownItem.module.css"


const DropdownItem = (props) => {
  return(
      <li className={style.dropdown_item}>
                {props.text}
      </li>
  );
}
export default DropdownItem;