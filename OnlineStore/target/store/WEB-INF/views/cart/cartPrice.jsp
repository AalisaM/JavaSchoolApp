${ ! empty curCart ? curCart.totalPrice :
             (! empty cartExtendedDTO.curCart ? cartExtendedDTO.curCart.totalPrice :
                     (! empty cartExtendedDTO.cartAnon ? cartExtendedDTO.cartAnon.totalPrice : "")
                     )
}

