function buildBar(divId, role){
    //let role = getCookie('role');
    let barButtonsQuantity = 0;
    let form;
    let li;
    let ul = $('<ul class="barStyle"></ul>');
    let bar = $('<nav class="barStyle"></nav>');
    let inputArray = [$('<input type="hidden" name="command" value="ShowDefaultPage"/>'),
                    $('<input type="hidden" name="command" value="ShowProductListPage"/>'),
                    null,
                    null];
    let buttonArray = [$('<button class="barStyle" type="submit" name="pressMe" value="press">Home</button>'),
                    $('<button class="barStyle" type="submit" name="pressMe" value="press">Products</button>'),
                    null,
                    null];
    if(role == 'UNREGISTERED_USER'){
        barButtonsQuantity = 4;
        inputArray[2] = $('<input type="hidden" name="command" value="ShowRegistrationPage"/>');
        inputArray[3] = $('<input type="hidden" name="command" value="ShowAuthenticationPage"/>');
        buttonArray[2] = $('<button class="barStyle" type="submit" name="pressMe" value="press">Sign Up</button>');
        buttonArray[3] = $('<button class="barStyle" type="submit" name="pressMe" value="press">Sign In</button>');
    }else {
        barButtonsQuantity = 4;
        inputArray[2] = $('<input type="hidden" name="command" value="ShowProfilePage"/>');
        inputArray[3] = $('<input type="hidden" name="command" value="Logout"/>');
        buttonArray[2] = $('<button class="barStyle" type="submit" name="pressMe" value="press">Profile</button>');
        buttonArray[3] = $('<button class="barStyle" type="submit" name="pressMe" value="press">Logout</button>');
    }
    if(role == 'ADMIN'){
        barButtonsQuantity = 5;
        inputArray[4] = $('<input type="hidden" name="command" value="ShowAdministrationPage"/>');
        buttonArray[4] = $('<button class="barStyle" type="submit" name="pressMe" value="press">Administration</button>');
    }
    for(let counter = 0; counter < barButtonsQuantity; counter++){
        li = $('<li class="barStyle"></li>');
        form = $('<form></form>');
        form.append(inputArray[counter]);
        form.append(buttonArray[counter]);
        li.append(form);
        ul.append(li);
    }

    bar.append(ul);
    $("#" + divId).append(bar);

}

function getCookie(cName) {
    const name = cName + "=";
    const cDecoded = decodeURIComponent(document.cookie); //to be careful
    const cArr = cDecoded.split('; ');
    let res;
    cArr.forEach(val => {
        if (val.indexOf(name) === 0) res = val.substring(name.length);
    })
    return res
}