$(function(){
    // [추가] 버튼 누르면 될 첨부파일 추가 가능
    let i = 0;
    $("#btnAdd").click(function(){
        $("#files").append(`
           <div class="input-group mb-2">
               <input class="form-control col-xs-3" type="file" name="upfile${i}"/>
               <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
           </div>`);
        i++;
    });

    // [삭제] 버튼 누르면 삭제될 첨부파일 id 담기
    $("[data-fileid-del]").click(function(){
       let fileId = $(this).attr("data-fileid-del");
       deleteFiles(fileId);
       $(this).parent().remove();
    });

    // Summernote 추가
    $("#content").summernote({
        height: 300,
    });
});

function deleteFiles(fileId) {
    // 삭제할 file 의 id값(들)을 #delFiles 에 담아 글 수정 완료 시 submit 되게 한다.
    $("#delFiles").append(`<input type="hidden" name="delfile" value="${fileId}">`)
}




