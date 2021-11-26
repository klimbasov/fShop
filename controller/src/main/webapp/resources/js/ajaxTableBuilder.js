let hrefBase = '?command=ShowProductInfoPage&product_id=';
let urlCommand = "FrontController?command=GetProductListPage&pageNum=";


function buildProductTable(tableDiv, navigationDiv, pageNum){
    $.ajax({
        dataType: "json",
        url: urlCommand + pageNum.toString(),
    }).done(function(data){
        _buildList(tableDiv, data);
        buildNavigation(navigationDiv, pageNum, '${pageQuantity}');
    });
}

function buildNavigation(nodeId, pageNum, pageQuantity, command){
    let navigationNav = $("<nav></nav>");
    let navigationUl = $("<ul></ul>");

    navigationUl.append($("<li></li>").append(createNavigateButton("<<", command, 1)));
    navigationUl.append($("<li></li>").append(createNavigateButton("<", command, pageNum > 1 ? pageNum-1 : 1)));

    if(pageQuantity > 7){
        if(pageNum < 5){
            let counter;
            for (counter = 1; counter <= 7; counter++){
                navigationUl.append($("<li></li>").append(createNavigateButton(counter.toString(), command, counter)));
            }
            let delimiterButton = createNavigateButton("...", "", 0);
            delimiterButton.disabled = true;
            navigationUl.append($("<li></li>").append(delimiterButton));
            navigationUl.append($("<li></li>").append(createNavigateButton(pageQuantity.toString(), command, pageQuantity)));
        }else
        if(pageNum > pageQuantity - 4){
            navigationUl.append($("<li></li>").append(createNavigateButton("1", command, 1)));
            let delimiterButton = createNavigateButton("...", "", 0);
            delimiterButton.disabled = true;
            navigationUl.append($("<li></li>").append(delimiterButton));
            let counter;
            for (counter = pageQuantity - 4; counter <= pageQuantity; counter++){
                navigationUl.append($("<li></li>").append(createNavigateButton(counter.toString(), command, counter)));
            }
        }else {
            navigationUl.append($("<li></li>").append(createNavigateButton("1", command, 1)));
            let delimiterButton = createNavigateButton("...", "", 0);
            delimiterButton.disabled = true;
            navigationUl.append($("<li></li>").append(delimiterButton));
            let counter;
            for (counter = pageNum - 2; counter <= pageNum + 2; counter++){
                navigationUl.append($("<li></li>").append(createNavigateButton(counter.toString(), command, counter)));
            }
            delimiterButton = createNavigateButton("...", "", 0);
            delimiterButton.disabled = true;
            navigationUl.append($("<li></li>").append(delimiterButton));
            navigationUl.append($("<li></li>").append(createNavigateButton(pageQuantity.toString(), command, pageQuantity)));
        }
    }else{
        let counter;
        for (counter = 1; counter <= pageQuantity; counter++){
            navigationUl.append($("<li></li>").append(createNavigateButton(counter.toString(), command, counter)));
        }
    }
    navigationUl.append($("<li></li>").append(createNavigateButton(">", command, pageNum < pageQuantity ? pageNum + 1 : pageQuantity)));
    navigationUl.append($("<li></li>").append(createNavigateButton(">>", command, pageQuantity)));

    navigationNav.append(navigationUl);
    $("#" + nodeId).append(navigationNav);
}

function createNavigateButton(text, command, pageNum){
    let form = $("<form></form>");
    let commandInput = $("<input type='hidden' id='command' name='command'>");
    let pageNumInput = $("<input type='hidden' id='pageNum' name='pageNum'>");
    commandInput.attr('value', command);
    pageNumInput.attr('value', pageNum);

    let buttonInput = $("<button type='submit'></button>");
    buttonInput.attr('content', text);
    form.append(commandInput, pageNumInput, buttonInput);
    return form;

}

function buildList(listDiv, navigateDiv, data){
    let jsonList = data;
    let product_list = $("<ul></ul>");

    for(let index in jsonList){
        let product_group = $("<li class='product_group'></li>");
        let product_image = $("<div class='product_slot_image'></div>");
        let product_info = $("<div class='product_slot_info'></div>");

        let product_image_content = $("<img class='product_image' src='resources/images/tick.jpg'>");
        let product_title = $("<div class='product_text'></div>");
        let product_title_href = $("<a class='product_href'></a>");
        let product_title_text = $("<span class='product_text'></span>");
        let product_price = $("<div class='product_text'></div>");

        let product_price_text = $("<span class='product_price'></span>");
        let product_description = $("<div class='product_text'></div>");

        let product_description_text = $("<span class='product_description'></span>");
        let product = jsonList[index];

        product_title_text.append(product['name']);
        product_price_text.append(product['price']);
        product_description_text.append(product['params']);
        product_title_href[0].setAttribute('href', hrefBase + product['id']);

        product_title_href.append(product_title_text);
        product_title.append(product_title_href);

        product_price.append(product_price_text);

        product_description.append(product_description_text);
        product_image.append(product_image_content);
        product_info.append(product_title, product_price, product_description);
        product_group.append(product_info);
        product_group.append(product_image);
        product_list.append(product_group);
    }
    $("#" + listDiv).append(product_list);
}

function _buildList(listDiv, data){
    let jsonList = data;
    let product_list = $("<ul></ul>");

    for(let index in jsonList){
        let product_group = $("<li></li>");
        let product_info = $("<div class='product_list info_group'></div>");

        let product_image = $("<img src='resources/images/tick.jpg'>");
        let product_title = $("<div class='product_list info_slot'></div>");
        let product_title_href = $("<a></a>");
        let product_title_text = $("<span></span>");
        let product_price = $("<div></div>");

        let product_price_text = $("<span class='product_list price'></span>");
        let product_description = $("<div></div>");

        let product_description_text = $("<span></span>");
        let product = jsonList[index];

        product_title_text.append(product['name']);
        product_price_text.append(product['price']);
        product_description_text.append(product['params']);
        product_title_href[0].setAttribute('href', hrefBase + product['id']);

        product_title_href.append(product_title_text);
        product_title.append(product_title_href);

        product_price.append(product_price_text);

        product_description.append(product_description_text);
        product_info.append(product_title, product_price, product_description);
        product_group.append(product_info);
        product_group.append(product_image);
        product_list.append(product_group);
    }
    $("#" + listDiv).append(product_list);
}

function getPage(parameter){
    alert(parameter);
}


$(document).ready(function () {
    let searchBar = document.getElementsByClassName('search')[0];
    let searchList = $("<ul></ul>");

    let searchLi = $("<li></li>");
    let filterLi = $("<li></li>");

    let searchLine = $("<input id='subname' name='subname' type='text' pattern='[a-zA-Z0-9]'>");
    let searchCommand = $("<input id='command' name='command' type='hidden' value='getProductList'>");

    let filterButton = $("<button type='button'>Filter</button>");
    let searchForm = $("<form id='searchForm'></form>");
    let filterForm = $("<form id='filterForm'></form>");
    let searchButton = $("<button type='submit'>Search</button>");

    searchForm.append(searchLine, searchCommand, searchButton);
    filterForm.append(filterButton);
    searchLi.append(searchForm);
    filterLi.append(filterForm);

    searchList.append(filterLi, searchLi);

    searchBar.append(searchList[0]);

});

function Search(tableDiv, navigateDiv) {
    let url ='FrontController?command=' + $("#command").val()
        + '&' + 'subname' + '='+ $("#subname").val();

    $.ajax({
        type: "GET",
        url: url,
        encode: true,
    }).done(function (data) {
        _buildList(tableDiv, navigateDiv, data);
    });
};