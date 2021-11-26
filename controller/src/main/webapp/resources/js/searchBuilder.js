// $(document).ready(function () {
//     let searchBar = document.getElementsByClassName('search')[0];
//     let searchList = $("<ul></ul>");
//
//     let searchLi = $("<li></li>");
//     let filterLi = $("<li></li>");
//
//     let searchLine = $("<input type='text' pattern='[a-zA-Z0-9]'>");
//     let filterButton = $("<button type='button'>Filter</button>");
//     let searchForm = $("<form id='searchForm'></form>");
//     let filterForm = $("<form id='filterForm'></form>");
//     let searchButton = $("<button type='submit'>Search</button>");
//
//     searchForm.append(searchLine, searchButton);
//     filterForm.append(filterButton);
//     searchLi.append(searchForm);
//     filterLi.append(filterForm);
//
//     searchList.append(filterLi, searchLi);
//
//     searchBar.append(searchList[0]);
//
// });

function Search() {
    let url ='FrontController?command=' + $("#command").val()
        + '&' + 'userName' + '='+ $("#userName").val()
        + '&' + 'password' + '='+ $("#password").val();

    $.ajax({
        type: "GET",
        url: url,
        encode: true,
    }).done(function (data) {
        if(data != ""){
            jsonData = JSON.parse(data);
            alert(jsonData['error']);
        }else{
            window.location.href = "FrontController";
        }
    });

};