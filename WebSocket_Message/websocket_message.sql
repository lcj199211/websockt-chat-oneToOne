-- websocket chat one to one

create table ws_message (
       msgId number primary key,
       msgContent varchar2(100) not null,
       fromName varchar2(30) not null,
       toName varchar2(30) not null,
       msgDate date not null,
       msgStatus number not null -- 0 δ����  1 �Ѷ�
);

-- ���� seq_message

select * from ws_message

-- truncate table ws_message

-- ҳ������޸� δ����Ϣ �޸ı��˷����ҵ���Ϣ�Ѷ�
update ws_message set msgstatus = 1 
where formname = 'toName' and toname = 'fromName'
and msgstatus = 0

