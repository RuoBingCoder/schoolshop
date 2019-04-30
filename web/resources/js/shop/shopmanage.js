$(function () {
    var shopId = getQueryString('shopId');
    var getShopManagementInfoUrl = '/school_shop/shopadmin/getshopmanagementinfo?shopId' + shopId;
    $.getJSON(getShopManagementInfoUrl, function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId !== undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $('#shopInfo').attr('href', '/school_shop/shopadmin/shoppage?shopId=' + shopId);
        }
    });
});