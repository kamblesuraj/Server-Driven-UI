export const actionResolver = {
  Action: {
    __resolveType(obj) {
      if (obj.inputIds) {
        return 'EditNameSubmitAction';
      }
      if (obj.url) {
        return 'URLAction';
      }
      if (obj.save && obj.unsave) {
        return 'FavouriteAction';
      }

      return null;
    }
  }
};
