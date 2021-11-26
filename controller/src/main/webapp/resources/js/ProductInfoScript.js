let successMsg = 'done';
let errorMsg = 'error';
let successMsgSrc = 'resources/images/success.png';
let errorMsgSrc = 'resources/images/success';
function getProductInfo(productId, nodeId, role){
    $.ajax({
        method: "GET",
        dataType: "json",
        url: "FrontController?command=GetProduct&product_id="+productId,
    }).done(function (data){
        buildPage(nodeId, data, productId, role);
    });
}

function buildPage(nodeId, product, product_id, role){
    let groupDiv = $("<div class='product_info group'></div>");
    let leftDiv = $("<div class='product_info half'></div>");
    let rightDiv = $("<div class='product_info half'></div>");
    let imageField = $("<div class='product_info field'></div>");
    let infoField = $("<div class='product_info field'></div>");
    let actionField = $("<div class='product_info field'></div>");
    let textContainer = $("<ul class='product_info text_list'></ul>");
    let actionContainer = $("<ul class='product_info action_list'></ul>");
    buildImage(imageField, successMsgSrc);
    buildInfo(textContainer, product);
    buildAction(actionContainer, role, product_id);
    infoField.append(textContainer);
    actionField.append(actionContainer);
    leftDiv.append(imageField);
    rightDiv.append(infoField, actionField);
    groupDiv.append(leftDiv,rightDiv);

    $("#"+nodeId).append(groupDiv);
}

function buildImage(node, src) {
    let image = $("<img>");
    image.attr("src", src);
    node.append(image);
}

function buildInfo(node, product) {
    let name = $("<li></li>");
    let price = $("<li></li>");
    let description = $("<li></li>");

    let name_name = $("<div class='product_info name'></div>");
    let price_name = $("<div class='product_info name'></div>");
    let description_name = $("<div class='product_info name'></div>");
    name_name.append($("<span></span>").append('name'));
    price_name.append($("<span></span>").append('price'));
    description_name.append($("<span></span>").append('description'));

    let name_value = $("<div class='product_info value'></div>");
    let price_value = $("<div class='product_info value'></div>");
    let description_value = $("<div class='product_info value'></div>");
    name_value.append($("<span></span>").append(product['name']));
    price_value.append($("<span></span>").append(product['price']));
    description_value.append($("<span></span>").append(product['description']));

    name.append(name_name,name_value);
    price.append(price_name,price_value);
    description.append(description_name, description_value);

    node.append(name, price, description);
}

function buildAction(node, role, product_id) {
    let li, input_command, form, input_product_id, button_submit;
    let isActions = true;
    switch (role) {
        case 'ADMIN':
            node.append(createLi('ChangeProduct', product_id.toString()));

            break;
        case 'REGISTERED_USER':
            node.append(createLi('OrderProduct', product_id.toString()));

            break;
        case 'UNREGISTERED_USER':
            isActions = false;
            break;
    }
}

function createLi(command, product_id){
    let li = $("<li></li>");
    let form = $("<form></form>");
    let input_command= $("<input type='hidden' id='command' name='command' />");
    input_command.attr('value', command);
    let input_product_id = $("<input type='hidden' id='product_id' name='product_id' />");
    input_product_id.attr('value', product_id);
    let button_submit = $('<button type="submit">Change</button>');
    form.append(input_command, input_product_id, button_submit);
    li.append(form);
    return li;
}

function orderProduct(productId){
    $.ajax({
        method: "POST",
        url: "FrontController?command=OrderProduct&id="+productId,
        success: function () {
            successOrdering(nodeId);
        },
        error: function () {
            failedOrdering(nodeId);
        }
    });
}

function successOrdering(){
    let div;
    if(div = $.getElementById(statusDivId) == null){
        div = $.createElement('div');
        div.id = statusDivId;
    }
    let img = $.createElement('img');
    let text = $('<p></p>').append(successMsg);
    img.src = successMsgSrc;
    div.append(img);
    div.append(text);
    $.body.append(div);
}

function failedOrdering(){
    let div;
    if(div = $.getElementById(statusDivId) == null){
        div = $.createElement('div');
        div.id = statusDivId;
    }
    let img = $.createElement('img');
    let text = $('<p></p>').append(errorMsg);
    img.src = errorMsgSrc;
    div.append(img);
    div.append(text);
    $.body.append(div);
}
