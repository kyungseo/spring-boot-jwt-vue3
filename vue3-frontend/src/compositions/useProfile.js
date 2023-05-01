import { reactive } from 'vue'

export const useProfile = () => {
  const state = reactive({ 
    id: 0,
    username: null,
    membername: null,
    email: null,
    roles: null,
    resume: []
  })

  const SET_DATA = (data) => {
    state[data.key] = data.value;
  }

  const setProfileData = (data) => {
    Object.keys(data).forEach((key) => {
      if (Object.keys(state).find((skey) => skey === key)) {
        SET_DATA({ key, value: data[key] });
      }
    })
  }

  return {
    user_data: state,
    setProfileData
  }
}
