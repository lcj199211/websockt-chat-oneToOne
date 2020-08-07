-- websocket chat one to one

create table ws_message (
       msgId number primary key,
       msgContent varchar2(100) not null,
       fromName varchar2(30) not null,
       toName varchar2(30) not null,
       msgDate date not null,
       msgStatus number not null -- 0 未读，  1 已读
);

-- 序列 seq_message

select * from ws_message

-- truncate table ws_message

-- 页面加载修改 未读消息 修改别人发给我的消息已读
update ws_message set msgstatus = 1 
where formname = 'toName' and toname = 'fromName'
and msgstatus = 0

