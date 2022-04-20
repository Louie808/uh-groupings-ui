describe("AdminController", function () {

    beforeEach(module("UHGroupingsApp"));
    beforeEach(module("ngMockE2E"));

    let scope;
    let controller;
    let gs;
    let mainCtrl;
    let uibModal;

    let fakeModal = {
        result: {
            then: function (confirmCallback, cancelCallBack) {
                this.confirmCallback = confirmCallback;
                this.cancelCallBack = cancelCallBack;
            }
        },
        close: function (item) {
            //the user clicks OK on the modal dialog, call the stored callback w/ the selected item
            this.result.confirmCallback(item);
        },
        dismiss: function (type) {
            // The user clicked on cancel, call the stored cancel callback
            this.result.cancelCallBack(type);
        }
    };

    beforeEach(inject(function ($rootScope, $controller, $uibModal, groupingsService) {
        scope = $rootScope.$new();
        controller = $controller("AdminJsController", {
            $scope: scope
        });
        gs = groupingsService;
        uibModal = $uibModal;
        spyOn($uibModal, "open").and.returnValue(fakeModal);
    }));

    it("should define the admin controller", function () {
        expect(controller).toBeDefined();
    });

    describe("init", function () {
        it("should call groupingsService.getAdminLists", function () {
            spyOn(gs, "getAdminLists");
            scope.init();
            expect(gs.getAdminLists).toHaveBeenCalled();
        });
    });

    describe("createRoleErrorModal", () => {
      it('should set scope.loading to false and open uibModal', () => {
        scope.loading = true;
        scope.createRoleErrorModal();

        expect(scope.loading).toBeFalse();
        expect(uibModal.open).toHaveBeenCalled();
      });
    });

    describe("createRemoveFromGroupsModal", () => {
      let options = {user: {uhUuid: 'testId'}, groupPaths: 'testPath', listName: 'testList'};

      it("should set scope variables to passed in option's object", () => {
        scope.userToRemove = {};
        scope.groupPaths = 'badPath';
        scope.listName = 'badList';
        scope.createRemoveFromGroupsModal(options);

        expect(scope.userToRemove).toEqual({uhUuid: 'testId'});
        expect(scope.groupPaths).toBe('testPath');
        expect(scope.listName).toBe('testList');
        expect(scope.ownerOfListName).toBe('');
      });

      it('should call showWarningRemovingSelfFromList function', () => {
        spyOn(scope, 'showWarningRemovingSelfFromList').and.callThrough();
        scope.createRemoveFromGroupsModal(options);

        expect(scope.showWarningRemovingSelfFromList).toHaveBeenCalled();
      });

      it('should call groupingsService', () => {
        spyOn(gs, 'getMemberAttributes').and.callThrough();
        scope.createRemoveFromGroupsModal(options);

        expect(gs.getMemberAttributes).toHaveBeenCalled();
      });
    });

    describe("getAdminListsCallbackOnSuccess", function () {
        let res = {};
        beforeEach(function () {
            res = {
                "allGroupingPaths": [
                    {
                        "path": "path",
                        "name": "name"
                    },
                    {
                        "path": "path",
                        "name": "name"
                    }
                ],
                "adminGroup": {
                    "members": [
                        {
                            "username": "username",
                            "uhUuid": "uhUuid",
                            "firstName": "firstName",
                            "lastName": "lastName",
                            "name": "name"
                        },
                        {
                            "username": "username",
                            "uhUuid": "uhUuid",
                            "firstName": "firstName",
                            "lastName": "lastName",
                            "name": "name"
                        }
                    ]
                }
            };
        });
        it("should call objToPageArray", function () {
            spyOn(scope, "objToPageArray");
            scope.getAdminListsCallbackOnSuccess(res);
            expect(scope.objToPageArray).toHaveBeenCalled();
        });
        it("should instantiate scope.pagedItemsAdmins", function () {
            scope.getAdminListsCallbackOnSuccess(res);
            expect(scope.pagedItemsAdmins).toBeDefined();
        });
        it("should instantiate scope.groupingsList", function () {
            scope.getAdminListsCallbackOnSuccess(res);
            expect(scope.groupingsList).toBeDefined();
        });
        it("should set scope.loading to false", function () {
            scope.getAdminListsCallbackOnSuccess(res);
            expect(scope.loading).toBeFalse();
        });
    });

    describe("searchForUserGroupingInformationOnSuccessCallback", function () {
        let res;
        beforeEach(function () {
            res = [
                {
                    "identifier": null,
                    "person": null,
                    "group": null,
                    "path": "path-to-grouping-name",
                    "name": "grouping-name",
                    "inBasis": false,
                    "inInclude": false,
                    "inExclude": false,
                    "inOwner": true,
                    "inBasisAndInclude": false,
                    "optOutEnabled": true,
                    "optInEnabled": false,
                    "selfOpted": false
                }];

        });
        it("should call scope.filter", function () {
            spyOn(scope, "filter");
            scope.searchForUserGroupingInformationOnSuccessCallback(res);
            expect(scope.filter).toHaveBeenCalled();
        });
        it("should set scope.personList equal to api response", function () {
            scope.searchForUserGroupingInformationOnSuccessCallback(res);
            expect(scope.personList).toEqual(res);
        });
        it("should set scope.user equal to scope.personToLookup", function () {
            scope.searchForUserGroupingInformationOnSuccessCallback(res);
            expect(expect(scope.user).toEqual(scope.personToLookup));
        });
        it("should set scope.loading to be false", function () {
            scope.searchForUserGroupingInformationOnSuccessCallback(res);
            expect(scope.loading).toBeFalse();
        });
    });

    describe("searchForUserGroupingInformationOnErrorCallback", function () {
        beforeEach(function () {
            scope.searchForUserGroupingInformationOnErrorCallback([]);
        });
        it("should set scope.loading to be false", function () {
            expect(scope.loading).toBeFalse();
        });
        it("should set scope.user equal to scope.personToLookup", function () {
            expect(expect(scope.user).toEqual(scope.personToLookup));
        });
    });

    describe("searchForUserGroupingInformation", function () {
        beforeEach(function () {
            scope.personToLookup = "iamtst01";
        });
        it("should call groupingsService.getMembershipAssignmentForUser", function () {
            spyOn(gs, "getMembershipAssignmentForUser").and.callThrough();
            scope.searchForUserGroupingInformation();
            expect(gs.getMembershipAssignmentForUser).toHaveBeenCalled();
        });
    });

    describe("removeFromGroupsCallbackOnSuccess", function () {
        let res;
        beforeEach(function () {
            scope.personToLookup = "iamtst01";
            res = {
                "uid": "iamtst01",
                "uhUuid": "iamtst01",
                "givenName": "tst01name",
                "cn": "tst01name",
                "sn": "tst01name"
            };
        });
        it("should set scope.emptySelect to true when scope.selectedGroupingsPaths is empty", function () {
            scope.removeFromGroupsCallbackOnSuccess(res);
            expect(scope.emptySelect).toBeTrue();
        });
    });

    describe("removeFromGroups", function () {
        beforeEach(function () {
            scope.personToLookup = "";
        });

        it("should call groupingsService.getMemberAttributes", function () {
            spyOn(gs, "getMemberAttributes");
            scope.removeFromGroups();
            expect(gs.getMemberAttributes).toHaveBeenCalled();
        });
    });


    describe("createGroupPathsAndNames", function () {
        let selectedGroupingsNames, selectedGroupingsPaths, selectedOwnedGroupings, selectedOwnedGroupingsNames, currentPage;
        beforeEach(function () {
            selectedGroupingsNames = [];
            selectedGroupingsPaths = [];
            selectedOwnedGroupings = [];
            selectedOwnedGroupingsNames = [];
            currentPage = [
                {
                    inBasis: false,
                    inInclude: false,
                    inExclude: false,
                    inOwner: true,
                    isSelected: true,
                    path: "grouping:grouping-name-group0",
                    name: "grouping-name0"
                },
                {
                    inBasis: false,
                    inInclude: true,
                    inExclude: false,
                    inOwner: false,
                    isSelected: true,
                    path: "grouping:grouping-name-group1",
                    name: "grouping-name1"
                },
                {
                    inBasis: false,
                    inInclude: false,
                    inExclude: false,
                    inOwner: true,
                    isSelected: false,
                    path: "grouping:grouping-name-group2",
                    name: "grouping-name2"
                }
            ];
        });
        it("should concatenate the subgroup onto the grouping paths", function () {
            scope.createGroupPathsAndNames(currentPage, selectedGroupingsNames, selectedGroupingsPaths, selectedOwnedGroupings, selectedOwnedGroupingsNames);
            expect(selectedGroupingsPaths[0]).toEqual("grouping:grouping-name-group0:owners");
            expect(selectedGroupingsPaths[1]).toEqual("grouping:grouping-name-group1:include");
        });
        it("should create the same amount of paths as names", function () {
            scope.createGroupPathsAndNames(currentPage, selectedGroupingsNames, selectedGroupingsPaths, selectedOwnedGroupings, selectedOwnedGroupingsNames);
            expect(selectedGroupingsPaths.length).toEqual(selectedGroupingsNames.length);
        });
        it("should fetch the grouping names", function () {
            scope.createGroupPathsAndNames(currentPage, selectedGroupingsNames, selectedGroupingsPaths, selectedOwnedGroupings, selectedOwnedGroupingsNames);
            expect(selectedGroupingsNames[0]).toEqual("grouping-name0");
            expect(selectedGroupingsNames[1]).toEqual("grouping-name1");
        });
        it("should not fetch data that is not selected", function () {
            scope.createGroupPathsAndNames(currentPage, selectedGroupingsNames, selectedGroupingsPaths, selectedOwnedGroupings, selectedOwnedGroupingsNames);
            expect(selectedGroupingsNames[2]).toBeUndefined();
            expect(selectedGroupingsPaths[2]).toBeUndefined();
            expect(selectedGroupingsNames.length).toEqual(2);
            expect(selectedGroupingsPaths.length).toEqual(2);
        });

    });

    describe("updateCheckBoxes", function () {
        beforeEach(function () {
            scope.pagedItemsPerson[scope.currentPagePerson] = {
                inBasis: false,
                inExclude: false,
                inInclude: true,
                isSelected: false
            };
        });
        it("should negate scope.checkAll", function () {
            let checkAll = scope.checkAll;
            scope.updateCheckBoxes();
            expect(scope.checkAll).toEqual(!checkAll);
        });
    });

    //describe
    //it
    //function

    describe("addAdmin", function () {

        it("should check that the admin to add is in the admin list", () => {
            scope.adminToAdd = "iamtst01";
            scope.addAdmin();
            expect(scope.listName).toBe("admins");
        });

        it("should check if the admin to add is empty", function () {
            scope.emptyInput = false;
            scope.addAdmin();
            expect(scope.emptyInput).toBeTrue();
        });

        it("should set waitingForImportResponse to false", function () {
            scope.waitingForImportResponse = true;
            scope.addAdmin();
            expect(scope.waitingForImportResponse).toBeFalse();

        });
    });

    // describe("createRemoveFromGroupsModal", () => {
    //     let options;
    //     options = {
    //         user: "iamtst01",
    //         groupPaths: ["tmp:kahlin:kahlin-large:include", "hawaii.edu:custom:test:listserv-tests:JTTEST-L:include"],
    //         listName: ["kahlin-large", "JTTEST-L"]
    //     }
    //     it(" should show success when modal responds w/ success", () => {
    //         scope.adminsList = ["iamtst01", "iamtst02", "iamtst03"];
    //         //Mock out the modal closing
    //         scope.createRemoveFromGroupsModal(options); //open the modal
    //         expect(gs.getMemberAttributes).toHaveBeenCalled();
    //         spyOn(gs, "getMemberAttributes");
    //         scope.removeModalInstance.dismiss("close");
    //         expect(scope.user).toBe("iamtst01");
    //         // expect(scope.resStatus).toBe(false);
    //     })
    // })

    describe("removeAdmin", function () {
        beforeEach(function () {
            scope.pagedItemsAdmins[0] = "zzzz";
        });
        it("should call scope.createRemoveModal", () => {
            scope.adminsList = ["iamtst01", "iamtst02", "iamtst03"];
            spyOn(scope, "createRemoveModal");
            scope.removeAdmin(0, 0);
            expect(scope.createRemoveModal).toHaveBeenCalled();
        });
        it("should call scope.createRemoveErrorModal", function () {
            spyOn(scope, "createRemoveErrorModal");
            scope.removeAdmin(0, 0);
            expect(scope.createRemoveErrorModal).toHaveBeenCalled();
        });
    });

});