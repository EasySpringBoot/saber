<#include 'common/head.ftl'>
<div class="layui-fluid" id="LAY-component-timeline">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                </div>
                <div class="layui-card-body" id="layui-card-body">
                    <ul class="layui-timeline">
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <h3 class="layui-timeline-title">
                                ${Knowledge.title}
                                </h3>
                                <h5 class="layui-timeline-title">
                                ${Knowledge.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}
                                </h5>
                                <textarea class="content" id="answer-${Knowledge.id}">
                                ${Knowledge.answer}
                                </textarea>
                            </div>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="/assets/layui/layui.all.js"></script>
<script>
    $(function () {
        layui.use('layedit', function () {
            var layedit = layui.layedit;
            layedit.build('answer-${Knowledge.id}', {height: 1000});
        });
    })
</script>
<#include 'common/foot.ftl'>