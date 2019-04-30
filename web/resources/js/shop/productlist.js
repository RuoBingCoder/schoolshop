/**
 * 2019.4.21 22:19
 */
$(function () {
    //获取此店铺下的商品列表url
    var listUrl = "/school_shop/productmanagement/getproductlistbyshop?pageIndex=1&pageSize=999";
    //下架商品url
    var statusUrl = "/school_shop/productmanagement/modifyproduct";

    /**
     * 获取店铺下的商品列表
     * @param listUrl
     * @param f
     */
    getProductList();

    function getProductList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                productList.map(function (item, index) {
                    var textOp = '下架';
                    var contraryStatus = 0;
                    //若状态值为0，表明是已下架的商品，操作变为上架（即点击上架按钮上架相关商品）
                    if (item.enableStatus === 0) {
                        // 若状态值为0，表明是已下架的商品，操作变为上架（即点击上架按钮上架相关商品）
                        textOp = '上架';
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
            }


        });

    }

    $('.product-wrap')
        .on(
            'click',
            'a',
            function (e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    window.location.href = '/school_shop/shopadmin/productoperation?productId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('status')) {
                    changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    window.location.href = '/myo2o/frontend/productdetail?productId='
                        + e.currentTarget.dataset.id;
                }
            });

    function changeItemStatus(id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function () {
            $.ajax({
                url: statusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getProductList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }


});