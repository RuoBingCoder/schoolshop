$(function () {
    var productUrl = '/school_shop/productshopcategory/getproductcatugorylist';
    var addbatchproductUrl = '/school_shop/productshopcategory/addbatchproductcategory';
    var deleteUrl = '/school_shop/productshopcategory/deleteproduct';
    getList();

    function getList() {
        $.getJSON(productUrl, function (data) {
            if (data.success) {
                var dataList = data.data;
                $('.category-wrap').html('');
                var html = '';
                dataList.map(function (item, index) {
                    html += ''
                        + '<div class="row row-product-category now">'
                        + '<div class="col-33 product-category-name">'
                        + item.productCategoryName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-33"><a href="#" class="button delete" data-id="'
                        + item.productCategoryId
                        + '">删除</a></div>'
                        + '</div>';
                });

                $('.category-wrap').append(html);

            }

        });
    }

//删除未提交后台
    $('.category-wrap').on('click', '.row-product-category.temp .delete', function (e) {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();


    });
    $('.category-wrap').on('click', '.row-product-category.now .delete', function (e) {
        var target = e.currentTarget;
        $.confirm("确定要删除？", function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productCategoryId: target.dataset.id
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast("删除成功！");
                        getList();
                    } else {
                        $.toast("删除失败！");
                    }

                }

            })

        })

    });

    $('#new').click(function () {
        // language=HTML
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
            + '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $('.category-wrap').append(tempHtml);

    });
    $('#submit').click(function () {
        var tempAddr = $('.temp');
        var productDataList = [];
        tempAddr.map(function (index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productDataList.push(tempObj);
            }
        });
        $.ajax({
            url: addbatchproductUrl,
            type: 'POST',
            data: JSON.stringify(productDataList),
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast("添加成功！");
                    getList();

                } else {
                    $.toast("添加失败！");
                }

            }
        })

    })

})
;




