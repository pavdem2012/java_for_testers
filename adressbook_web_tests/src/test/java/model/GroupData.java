package model;

import java.util.List;

public record GroupData(String id, String name, String header, String footer) {
    public GroupData() {
        this("", "", "", "");
    }

    public GroupData withName(String name) {
        return new GroupData(this.id, name, this.header, this.footer);
    }

    public GroupData withHeader(String header) {
        return new GroupData(this.id, this.name, header, this.footer);
    }

    public GroupData withFooter(String footer) {
        return new GroupData(this.id, this.name, this.header, footer);
    }

    public GroupData withId(String id) {
        return new GroupData(id, this.name, this.header, footer);
    }

    // Вспомогательный метод для замены группы в списке
    public void replaceGroupInList(List<GroupData> list, String groupId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id().equals(groupId)) {
                list.set(i, this);
                return;
            }
        }
        throw new RuntimeException("Group not found with id: " + groupId);
    }
}