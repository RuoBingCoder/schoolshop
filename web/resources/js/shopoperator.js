$(function () {
    var shopId = getQueryString('shopId');
    var edit = !!shopId;
    var initUrl = '/school_shop/shopadmin/getshopinitinfo';
    var registerUrl = '/school_shop/shopadmin/registerShop';
    var shopInfoUrl = '/school_shop/shopadmin/getshopid?shopId=' + shopId;
    var modifyUrl = '/school_shop/shopadmin/modifyShop';
    // alert(initUrl);
    //初始化
    if (!edit) {
        getShopInitInfo();
    } else {
        getShopInfo(shopId);

    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="' + shop.shopCategory.shopCategoryId + '"selected>' + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='" + shop.area.areaId + "']").attr("selected", "selected");


            }

        });

    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var categoryList = data.categoryList;
                var areaList = data.areaList;
                var tempHtml = '';
                var tempAreaHtml = '';
                categoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);

            }

        });
    }

    $('#submit').click(function () {
        var shop = {};
        if (edit) {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.phone = $('#shop-phone').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId:
                $('#shop-category').find('option').not(function () {
                    return !this.selected;

                }).data('id')


        };
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;

            }).data('id')


        };
        var shopImg = $('#shop-img')[0].files[0];
        var fromData = new FormData();


        fromData.append('shopImg', shopImg);
        fromData.append('shopStr', JSON.stringify(shop));
        var verifyCodeActual = $('#j_kaptcha').val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码！");
            return
        }
        //传入图片
        fromData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url: (edit ? modifyUrl : registerUrl),
            type: 'POST',
            data: fromData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {

                    alert("提交成功！");
                } else {
                    $.toast("提交失败");

                }
                $('#kaptcha_img').click();
            }

        });
    });


});