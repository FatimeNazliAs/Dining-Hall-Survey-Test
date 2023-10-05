/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dininghall.view.helpdesk;

import dininghall.service.helpdesk.HDeskCategoryService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import dininghall.domain.helpdesk.HDeskCategory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "hDeskCategoryInsertView")
@ViewScoped
public class HDeskCategoryInsertView implements Serializable {

    private HDeskCategory newCategory;

    @ManagedProperty("#{hDeskCategoryService}")
    private HDeskCategoryService service;

    private int categoryId;
    private String name;
    private String description;
    private int parentCatId;
    private TreeNode rootNode;
    private List<HDeskCategory> rootCategoryList;
    private TreeNode selectedNode;
    private TreeNode previousNode;

    @PostConstruct
    public void init() {
        newCategory = new HDeskCategory();

        rootNode = new DefaultTreeNode("Root", null);

        rootCategoryList = service.getHDeskCategorySubListByParentCatId(0);
        for (HDeskCategory category : rootCategoryList) {
            TreeNode node = new DefaultTreeNode(category.getCatId() + " - " + category.getName());
            rootNode.getChildren().add(node);
        }

    }

    public void onNodeSelect(NodeSelectEvent event) {
        /**
         for (TreeNode node :
         rootNode.getChildren())
         {
         node.setExpanded(false);
         }*/


        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.toString());
        if (!selectedNode.toString().equalsIgnoreCase("ROOT")) {
            String[] parts = selectedNode.toString().split("-");
            int selectedHDeskCategoryId = Integer.parseInt(parts[0].trim());

            // Selected node info
            HDeskCategory selectedCategory = service.getHDeskCategoryByHDeskCategoryId(selectedHDeskCategoryId);

            parentCatId = selectedHDeskCategoryId;

            TreeNode tn = selectedNode;
            while (tn.getParent() != null) {
                tn.setExpanded(true);
                tn = tn.getParent();
            }

        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public void resetForm() {
        name = "";
        description = "";
        parentCatId = 0;
    }

    public void insertHDeskCategory() {
        newCategory.setName(name);
        newCategory.setDescription(description);
        newCategory.setParentCatId(parentCatId);

        if (service.insertHDeskCategory(newCategory)) {
            FacesMessage msg = new FacesMessage("HDeskCategory added successfully", newCategory.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();
        } else {
            FacesMessage msg = new FacesMessage("System error, category not added.", newCategory.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public HDeskCategoryService getService() {
        return service;
    }

    public void setService(HDeskCategoryService service) {
        this.service = service;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParentCatId() {
        return parentCatId;
    }

    public void setParentCatId(int parentCatId) {
        this.parentCatId = parentCatId;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(TreeNode previousNode) {
        this.previousNode = previousNode;
    }

    public List<HDeskCategory> getRootCategoryList() {
        return rootCategoryList;
    }

    public void setRootCategoryList(List<HDeskCategory> rootCategoryList) {
        this.rootCategoryList = rootCategoryList;
    }

    public HDeskCategory getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(HDeskCategory newCategory) {
        this.newCategory = newCategory;
    }
}
