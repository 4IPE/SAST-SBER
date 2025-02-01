'use client'

import {useRouter} from 'next/navigation'
import {FolderGit2, Home, LogIn, UserCircle, Users2} from 'lucide-react'
import {ThemeToggle} from './ThemeToggle'
import {useTheme} from '@/contexts/ThemeContext'
import apiClient from "@/app/config/apiClient";
import useUserData from "@/app/config/useUserData";

export default function Sidebar() {
    const router = useRouter()
    const {theme} = useTheme()
    const user = useUserData()
    const iconClass = `w-6 h-6 ${theme === 'dark' ? 'text-text-primary' : 'text-text-secondary'}`

    const handleLogout = async (e: React.FormEvent) => {
        e.preventDefault()
        if (user && window.confirm("Вы уверены, что хотите выйти из аккаунта?")) {
            await apiClient.post('/auth/logout')
            window.location.href = "/login";
        }
    }

    if (user) {
        return (
            <aside
                className="fixed left-0 top-0 h-full w-20 bg-card flex flex-col items-center justify-between py-8 border-r border-border">
                <>
                    <nav className="flex flex-col items-center space-y-8">
                        <div
                            className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                            onClick={() => router.push('/')}
                            role="button"
                            tabIndex={0}
                        >
                            <Home className={iconClass}/>
                        </div>

                        <div
                            className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                            onClick={() => router.push('/projects')}
                            role="button"
                            tabIndex={0}
                        >
                            <FolderGit2 className={iconClass}/>
                        </div>

                        <div
                            className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                            onClick={() => router.push('/teams')}
                            role="button"
                            tabIndex={0}
                        >
                            <Users2 className={iconClass}/>
                        </div>
                    </nav>

                    <div className="flex flex-col items-center space-y-8">
                        <div
                            className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                            onClick={() => router.push('/profile')}
                            role="button"
                            tabIndex={0}
                        >
                            <UserCircle className={iconClass}/>
                        </div>

                        <div
                            className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                            onClick={handleLogout}
                            role="button"
                            tabIndex={0}
                        >
                            <LogIn className={iconClass}/>
                        </div>

                        <ThemeToggle/>
                    </div>

                </>
            </aside>
        )
    }

    return (
        <aside
            className="fixed left-0 top-0 h-full w-20 bg-card flex flex-col items-center justify-end py-8 border-r border-border gap-8">
            <div
                className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                onClick={() => router.push("/login")}
                role="button"
                tabIndex={0}
            >
                <LogIn className={iconClass}/>
            </div>
            <ThemeToggle/>
        </aside>
    )

}